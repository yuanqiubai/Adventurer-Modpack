import struct

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

def read_tag(reader):
    tag_type = reader.read_byte()
    if tag_type == 0:
        return None, None
    name = reader.read_string()
    value = read_payload(reader, tag_type)
    return name, value

def read_payload(reader, tag_type):
    if tag_type == 1:
        return reader.read_byte()
    elif tag_type == 2:
        return reader.read_short()
    elif tag_type == 3:
        return reader.read_int()
    elif tag_type == 4:
        return reader.read_long()
    elif tag_type == 5:
        return reader.read_float()
    elif tag_type == 6:
        return reader.read_double()
    elif tag_type == 8:
        return reader.read_string()
    elif tag_type == 9:
        subtag = reader.read_byte()
        length = reader.read_int()
        result = []
        for _ in range(length):
            result.append(read_payload(reader, subtag))
        return result
    elif tag_type == 10:
        result = {}
        while True:
            k, v = read_tag(reader)
            if k is None:
                break
            result[k] = v
        return result

with open(r"F:\ATcraft-Addon-164\tool\test_engine.dat", "rb") as f:
    raw = f.read()

reader = NBTReader(raw)
root_type = reader.read_byte()
root_name = reader.read_string()
root = read_payload(reader, root_type)

if "root" in root:
    r = root["root"]
    if "AMMO" in r:
        ammo_list = r["AMMO"]
        print("Found {} ammo items:\n".format(len(ammo_list)))
        for i, ammo in enumerate(ammo_list):
            name = ammo.get("item_internalName", "???")
            print("  {}. {}".format(i+1, name))
    else:
        print("No AMMO section found")
        print("Keys:", list(r.keys()))
else:
    print("No root found")
