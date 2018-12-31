import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(findIndent("  adc"));
        List<String> list = new ArrayList<>();
        list.add("if control_statement_1:");
        list.add("    indent = 4");
        list.add("    while control_statement_2 = 4:");
        list.add("        if True:");
        list.add("            print 'sdfsadfdsaf'");
        list.add("        elif control_statement_3:");
        list.add("            print 1");
        list.add("            print 2");

        list.add("        print 'Done with this part'");
        list.add("    print 'Done with while loop'");
        list.add("elif control 4:");
        list.add("  indent = 2");
        list.add("  for i in range(0, 1, 3):");
        list.add("    print(i)");
        list.add("else:");
        list.add("    indent = rand");
        list.add("    print 'yea'");

        System.out.println("Expect 0 Actual " + getFirstViolationLine(list));

        List<String> list2 = new ArrayList<>();
        list2.add("if True:");
        list2.add("   print 1");
        list2.add("   print 2");
        list2.add("elif False:");
        list2.add(" print 3");
        list2.add(" print 4");
        list2.add("else:");
        list2.add(" print 5");
        list2.add(" print 6");

        System.out.println("Expect 0 Actual " + getFirstViolationLine(list2));

    }

    private static int findIndent(String line) {
        String trimmedStart = line.replaceFirst("^\\s+", "");
        return line.length() - trimmedStart.length();
    }

    public static int getFirstViolationLine(List<String> lines) {
        Stack<Integer> indents = new Stack<>();
        indents.push(0);
        int lineNum = 0;

        // check first line indentation
        // if not 0, return 0
        if (findIndent(lines.get(lineNum)) != 0) {
            return 1;
        }
        lineNum++;

        while (lineNum < lines.size()) {
            String prevLine = lines.get(lineNum - 1);
            String line = lines.get(lineNum);
            int indent = findIndent(line);

            // if previous line is a control statement, check that this line is indented and
            // store the new indent in the Stack
            if (prevLine.charAt(prevLine.length() - 1) == ':') {
                if (indent > indents.peek()) {
                    indents.push(indent);
                } else {
                    return lineNum + 1;
                }
            }else {
                // if previousLine is not a controlLine but this one is, check that it matches the indent of the previousLine or is indented like the previous block
                if (indent != indents.peek()) {
                    int prevIndent = indents.pop();
                    while (indent != prevIndent && indents.size() != 1) {
                        prevIndent = indents.pop();
                    }

                    if (indent == prevIndent) {
                        indents.push(prevIndent);
                    } else if (indent != indents.peek()){
                        return lineNum + 1;
                    }
                }
            }

            lineNum++;
        }
        return 0;
    }


}
