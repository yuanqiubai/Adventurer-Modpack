import gzip
import struct
import io
import sys

class NBTReader:
    def __init__(self, data):
        self.data = data
        self.pos = 0

    def read_byte(self):
        b = self.data[self.pos]
        self.pos += 1
        return b

    def read_short(self):
        v = struct.unpack('>h', self.data[self.pos:self.pos+2])[0]
        self.pos += 2
        return v

    def read_int(self):
        v = struct.unpack('>i', self.data[self.pos:self.pos+4])[0]
        self.pos += 4
        return v

    def read_long(self):
        v = struct.unpack('>q', self.data[self.pos:self.pos+8])[0]
        self.pos += 8
        return v

    def read_float(self):
        v = struct.unpack('>f', self.data[self.pos:self.pos+4])[0]
        self.pos += 4
        return v

    def read_double(self):
        v = struct.unpack('>d', self.data[self.pos:self.pos+8])[0]
        self.pos += 8
        return v

    def read_string(self):
        length = self.read_short()
        s = self.data[self.pos:self.pos+length].decode('utf-8')
        self.pos += length
        return s

    def read_byte_array(self):
        length = self.read_int()
        arr = self.data[self.pos:self.pos+length]
        self.pos += length
        return arr

    def read_int_array(self):
        length = self.read_int()
        arr = []
        for _ in range(length):
            arr.append(self.read_int())
        return arr

    def read_tag(self):
        tag_type = self.read_byte()
        if tag_type == 0:  # TAG_End
            return None, None

        name = self.read_string()
        
        if tag_type == 1:   # TAG_Byte
            return name, self.read_byte()
        elif tag_type == 2: # TAG_Short
            return name, self.read_short()
        elif tag_type == 3: # TAG_Int
            return name, self.read_int()
        elif tag_type == 4: # TAG_Long
            return name, self.read_long()
        elif tag_type == 5: # TAG_Float
            return name, self.read_float()
        elif tag_type == 6: # TAG_Double
            return name, self.read_double()
        elif tag_type == 7: # TAG_Byte_Array
            return name, self.read_byte_array()
        elif tag_type == 8: # TAG_String
            return name, self.read_string()
        elif tag_type == 9: # TAG_List
            list_type = self.read_byte()
            length = self.read_int()
            items = []
            for _ in range(length):
                _, item = self.read_tag_payload(list_type)
                items.append(item)
            return name, (list_type, items)
        elif tag_type == 10: # TAG_Compound
            compound = {}
            while True:
                n, v = self.read_tag()
                if n is None and v is None:
                    break
                compound[n] = v
            return name, compound
        elif tag_type == 11: # TAG_Int_Array
            return name, self.read_int_array()
        return name, None

    def read_tag_payload(self, tag_type):
        if tag_type == 1: return None, self.read_byte()
        elif tag_type == 2: return None, self.read_short()
        elif tag_type == 3: return None, self.read_int()
        elif tag_type == 4: return None, self.read_long()
        elif tag_type == 5: return None, self.read_float()
        elif tag_type == 6: return None, self.read_double()
        elif tag_type == 7: return None, self.read_byte_array()
        elif tag_type == 8: return None, self.read_string()
        elif tag_type == 9:
            list_type = self.read_byte()
            length = self.read_int()
            items = []
            for _ in range(length):
                _, item = self.read_tag_payload(list_type)
                items.append(item)
            return None, (list_type, items)
        elif tag_type == 10:
            compound = {}
            while True:
                n, v = self.read_tag()
                if n is None and v is None:
                    break
                compound[n] = v
            return None, compound
        elif tag_type == 11: return None, self.read_int_array()
        return None, None

def read_nbt(filepath):
    with gzip.open(filepath, 'rb') as f:
        data = f.read()
    reader = NBTReader(data)
    tag_type = reader.read_byte()
    name = reader.read_string()
    _, root = reader.read_tag_payload(tag_type)
    return root

def write_nbt(filepath, root):
    buf = io.BytesIO()
    buf.write(b'\x0a')  # TAG_Compound
    # write name (empty string for root)
    buf.write(b'\x00\x00')
    write_compound(buf, root)
    
    with gzip.open(filepath, 'wb') as f:
        f.write(buf.getvalue())

def write_tag(buf, tag_type, name, value):
    buf.write(bytes([tag_type]))
    name_bytes = name.encode('utf-8')
    buf.write(struct.pack('>h', len(name_bytes)))
    buf.write(name_bytes)
    write_payload(buf, tag_type, value)

def write_payload(buf, tag_type, value):
    try:
        if tag_type == 1:
            if isinstance(value, bool):
                buf.write(bytes([1 if value else 0]))
            else:
                buf.write(bytes([value]))
        elif tag_type == 2:
            buf.write(struct.pack('>h', value))
    elif tag_type == 3:
        buf.write(struct.pack('>i', value))
    elif tag_type == 4:
        buf.write(struct.pack('>q', value))
    elif tag_type == 5:
        buf.write(struct.pack('>f', value))
    elif tag_type == 6:
        buf.write(struct.pack('>d', value))
    elif tag_type == 7:
        buf.write(struct.pack('>i', len(value)))
        buf.write(value)
    elif tag_type == 8:
        s = value.encode('utf-8')
        buf.write(struct.pack('>h', len(s)))
        buf.write(s)
    elif tag_type == 9:
        list_type, items = value
        buf.write(bytes([list_type]))
        buf.write(struct.pack('>i', len(items)))
        for item in items:
            write_payload(buf, list_type, item)
    elif tag_type == 10:
        write_compound(buf, value)
    elif tag_type == 11:
        buf.write(struct.pack('>i', len(value)))
        for v in value:
            buf.write(struct.pack('>i', v))

def write_compound(buf, compound):
    for name, value in compound.items():
        tag_type = get_tag_type(value)
        write_tag(buf, tag_type, name, value)
    buf.write(b'\x00')  # TAG_End

def get_tag_type(value):
    if isinstance(value, bool) or isinstance(value, int) and not isinstance(value, bool):
        if isinstance(value, bool):
            return 1
        if -128 <= value <= 127:
            return 1  # byte
        elif -32768 <= value <= 32767:
            return 2  # short
        elif -2147483648 <= value <= 2147483647:
            return 3  # int
        else:
            return 4  # long
    elif isinstance(value, float):
        return 6  # double
    elif isinstance(value, str):
        return 8
    elif isinstance(value, bytes):
        return 7
    elif isinstance(value, tuple) and len(value) == 2:
        return 9
    elif isinstance(value, dict):
        return 10
    return 0

def list_gamerules(level_path):
    root = read_nbt(level_path)
    data = root.get('Data', root)
    gamerules = data.get('GameRules', {})
    print("=== 当前 GameRules ===")
    for k, v in sorted(gamerules.items()):
        print(f"  {k}: {v}")
    return root, data, gamerules

# Main
level_path = sys.argv[1] if len(sys.argv) > 1 else None
if not level_path:
    print("Usage: python nbt_tool.py <level.dat path>")
    sys.exit(1)

root, data, gamerules = list_gamerules(level_path)

# Enable keepInventory
if 'keepInventory' not in gamerules or gamerules.get('keepInventory') != 'true':
    gamerules['keepInventory'] = 'true'
    data['GameRules'] = gamerules
    root['Data'] = data
    write_nbt(level_path, root)
    print("\n✅ keepInventory 已开启")
else:
    print("\nkeepInventory 已经是开启状态")
