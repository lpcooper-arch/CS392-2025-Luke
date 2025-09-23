public class Assign03_02 {
    static boolean balncedq(String text) {
	//
	// There are only '(', ')', '[', ']', '{', and '}'
	// appearing in [text]. This method should return
	// true if and only if the parentheses/brackets/braces
	// in [text] are balenced.
	// Your solution must make proper use of MyStack!
	//

		MyStackList<Character> stack = new MyStackList<>();
		
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '(' || c == '{' || c == '[') {
				stack.push$exn(c);
			}
			else if (c == ')' || c == '}' || c == ']') {
				if (stack.isEmpty()) return false;
				else if (!oppositeChar(stack.pop$exn(), c)) return false;
			}

		}
		return true;		
    }

    public static void main(String[] args) {

		String[] text = {"({[[()]]})", "({()[({})]})", "({()[({})])}"};

		for (String line : text) {
			System.out.println(balncedq(line));
		}

		/*
		 * expected output:
		 * true
		 * true
		 * false
		 */
    }
	private static boolean oppositeChar(char first, char second) {
		if (first == '(') return second == ')';
		else if (first == '{') return second == '}';
		else if (first == '[') return second == ']';

		return false;
	}
}
