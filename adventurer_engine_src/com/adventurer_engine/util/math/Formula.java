/*
 * Decompiled with CFR 0.152.
 */
package com.adventurer_engine.util.math;

import com.adventurer_engine.util.algorithm.BinaryTree;
import com.adventurer_engine.util.math.BracketNotMatchException;
import java.io.PrintStream;
import java.util.Map;

public class Formula {
    private final BinaryTree tree = new BinaryTree();

    public Formula(String formulaText) {
        String formula = formulaText.replaceAll(" ", "");
        Formula.str2Tree(this.tree, formula);
    }

    public double calculation(Map<String, Double> nums) {
        return Formula.calculation_internal(this.tree, nums);
    }

    public void printTree(PrintStream out) {
        Formula.printTree_internal(this.tree, 0, out);
    }

    private static void printTree_internal(BinaryTree subRoot, int depth, PrintStream out) {
        for (int i = 0; i < depth; ++i) {
            out.print("   |");
        }
        out.println(subRoot.value);
        if (subRoot.lchild != null) {
            Formula.printTree_internal(subRoot.lchild, depth + 1, out);
        }
        if (subRoot.rchild != null) {
            Formula.printTree_internal(subRoot.rchild, depth + 1, out);
        }
    }

    private static void str2Tree(BinaryTree subRoot, String fk) {
        char now;
        int i;
        int lr = 0;
        int firstPlus = 0;
        int firstMult = 0;
        boolean light = true;
        boolean light2 = true;
        for (i = fk.length() - 1; i >= 0; --i) {
            now = fk.charAt(i);
            if (now == ')') {
                ++lr;
                continue;
            }
            if (now == '(') {
                --lr;
                continue;
            }
            if ((now == '+' || now == '-') && firstPlus == 0 && lr == 0) {
                firstPlus = i;
                light = now == '+';
                continue;
            }
            if (now != '*' && now != '/' || firstMult != 0 || lr != 0) continue;
            firstMult = i;
            light2 = now == '*';
        }
        if (lr != 0) {
            throw new BracketNotMatchException(fk);
        }
        String huber = null;
        String huber2 = null;
        if (firstPlus != 0) {
            subRoot.lchild = new BinaryTree();
            subRoot.rchild = new BinaryTree();
            String string = subRoot.value = light ? "+" : "-";
            if (fk.charAt(0) == '(') {
                lr = 1;
                for (i = 1; i < firstPlus; ++i) {
                    now = fk.charAt(i);
                    if (now == '(') {
                        ++lr;
                    }
                    if (now == ')' && --lr == 0) break;
                }
                if (i == firstPlus - 1) {
                    huber = fk.substring(1, firstPlus - 1);
                }
            }
            if (huber == null) {
                huber = fk.substring(0, firstPlus);
            }
            if (fk.charAt(firstPlus + 1) == '(') {
                lr = 1;
                for (i = firstPlus + 2; i < fk.length(); ++i) {
                    now = fk.charAt(i);
                    if (now == '(') {
                        ++lr;
                    }
                    if (now == ')' && --lr == 0) break;
                }
                if (i == fk.length() - 1) {
                    huber2 = fk.substring(firstPlus + 2, i);
                }
            }
            if (huber2 == null) {
                huber2 = fk.substring(firstPlus + 1);
            }
            Formula.str2Tree(subRoot.lchild, huber);
            Formula.str2Tree(subRoot.rchild, huber2);
        } else if (firstMult != 0) {
            subRoot.lchild = new BinaryTree();
            subRoot.rchild = new BinaryTree();
            String string = subRoot.value = light2 ? "*" : "/";
            if (fk.charAt(0) == '(') {
                lr = 1;
                for (i = 1; i < firstMult; ++i) {
                    now = fk.charAt(i);
                    if (now == '(') {
                        ++lr;
                    }
                    if (now == ')' && --lr == 0) break;
                }
                if (i == firstMult - 1) {
                    huber = fk.substring(1, firstMult - 1);
                }
            }
            if (huber == null) {
                huber = fk.substring(0, firstMult);
            }
            if (fk.charAt(firstMult + 1) == '(') {
                lr = 1;
                for (i = firstMult + 2; i < fk.length(); ++i) {
                    now = fk.charAt(i);
                    if (now == '(') {
                        ++lr;
                    }
                    if (now == ')' && --lr == 0) break;
                }
                if (i == fk.length() - 1) {
                    huber2 = fk.substring(firstMult + 2, i);
                }
            }
            if (huber2 == null) {
                huber2 = fk.substring(firstMult + 1);
            }
            Formula.str2Tree(subRoot.lchild, huber);
            Formula.str2Tree(subRoot.rchild, huber2);
        } else {
            subRoot.value = fk;
        }
    }

    private static double calculation_internal(BinaryTree tree, Map<String, Double> nums) {
        if (tree.lchild == null && tree.rchild == null) {
            try {
                return Double.parseDouble(tree.value);
            }
            catch (Exception e) {
                try {
                    return nums.get(tree.value);
                }
                catch (Exception ep) {
                    return 0.0;
                }
            }
        }
        assert (tree.lchild != null);
        double left = Formula.calculation_internal(tree.lchild, nums);
        double right = Formula.calculation_internal(tree.rchild, nums);
        if ("+".equals(tree.value)) {
            return left + right;
        }
        if ("-".equals(tree.value)) {
            return left - right;
        }
        if ("*".equals(tree.value)) {
            return left * right;
        }
        if ("/".equals(tree.value)) {
            return left / right;
        }
        return 0.0;
    }
}

