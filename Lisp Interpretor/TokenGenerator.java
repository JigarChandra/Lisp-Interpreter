//chandra.67@osu.edu

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Stack;
import java.util.ArrayList;
import java.util.regex.Matcher;

class OtherFuncs
{
	public static SExp EVAL(SExp exp, SExp aList, SExp dList) {
		try {
		if (SExp.ATOM(exp).name.equals("T")) {
			if (SExp.INT(exp).name.equals("T")) {
				return exp;
			} else if (SExp.EQ(exp, SExp.atomsLeft[0]).name.equals("T")) {
				return SExp.atomsLeft[0];
			} else if (SExp.EQ(exp, SExp.atomsLeft[1]).name.equals("T")) {
				return SExp.atomsLeft[1];
			} else if (IN(exp, aList).name.equals("T")) {
				return GETVAL(exp, aList);
			} else {
				System.out.println("Unbound Variable");
			}
		}

		else if (SExp.ATOM(SExp.CAR(exp)).name.equals("T")) {
			if (SExp.EQ(SExp.CAR(exp), SExp.atomsLeft[17]).name.equals("T")) {
				
					if (argLength(SExp.CDR(exp)) > 1) {
						System.out.println("Error");
						return null;
					} else if (argLength(SExp.CDR(exp)) < 1) {
						System.out.println("Error");
						return null;
					} else {
				return SExp.CAR(SExp.CDR(exp));
					}
			} else if (SExp.EQ(SExp.CAR(exp), SExp.atomsLeft[16]).name.equals("T")) {
				SExp.defunCheck = true;
				return EVCON(SExp.CDR(exp), aList, dList);
			} else if (SExp.EQ(SExp.CAR(exp), SExp.atomsLeft[18]).name.equals("T")) 
			{
				if(SExp.defunCheck)
				{
					System.out.println("Error");
					return null;
				}
				else {
					SExp.defunCheck = true;
				}
					if (SExp.EQ(checkArgs(SExp.CAR(SExp.CDR(SExp.CDR(exp)))), SExp.atomsLeft[1]).name.equals("T")) {
						return null;
					}
					for (int p = 0; p < 20; p++) {
						if(SExp.CAR(SExp.CDR(exp)).kind!=2)
						{
							System.out.println("Error");
							return null;
						}
						if (SExp.CAR(SExp.CDR(exp)).name.equals(SExp.atomsLeft[p].name)
								||SExp.CAR(SExp.CDR(exp)).name.equals("+")
								||SExp.CAR(SExp.CDR(exp)).name.equals("-")) {
							System.out.println("Error");
							return null;
						}
					}
				return ADDDEFINITION(exp);
			} else {
				try {
					return APPLY(SExp.CAR(exp), EVLIS(SExp.CDR(exp), aList, dList),
							aList, dList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("Error");
		}

		return null;
		} catch (Exception e) {
			return null;
		}
	}


	public static SExp EVLIS(SExp exp, SExp aList, SExp dList) {
		try {
		if (SExp.NULL(exp).name.equals("T")) {
			return SExp.atomsLeft[1];
		} else {
			return SExp.CONS(EVAL(SExp.CAR(exp), aList, dList),
					EVLIS(SExp.CDR(exp), aList, dList));
		}
		} catch (Exception e) {
			return null;
		}
	}


	public static SExp EVCON(SExp exp, SExp aList, SExp dList) {
		try {
		if (SExp.NULL(exp).name.equals("T")) {
			return SExp.atomsLeft[1];
			} else if (EVAL(SExp.CAR(SExp.CAR(exp)), aList, dList).kind == 2) {
				if (!(EVAL(SExp.CAR(SExp.CAR(exp)), aList, dList).name.equals("NIL"))) {
					return EVAL(SExp.CAR(SExp.CDR(SExp.CAR(exp))), aList, dList);
				} else {
					return EVCON(SExp.CDR(exp), aList, dList);
				}

			} else {
			return EVAL(SExp.CAR(SExp.CDR(SExp.CAR(exp))), aList, dList);
		}
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp IN(SExp exp, SExp list1) {
		try {
		if (SExp.NULL(list1).name.equals("T")) {
			return SExp.atomsLeft[1];
		} else if (SExp.CAR(list1).name != null && SExp.CAR(list1).name.equals("MARKER")) {
			return SExp.atomsLeft[1];
		}
 else if (SExp.EQ(exp, SExp.CAR(SExp.CAR(list1))).name.equals("T")) {
			return SExp.atomsLeft[0];
		} else {
			return IN(exp, SExp.CDR(list1));
		}
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp GETVAL(SExp exp, SExp list1) {
		try {
		if (SExp.NULL(list1).name.equals("T")) {
			return SExp.atomsLeft[1];
		} else if (SExp.EQ(exp, SExp.CAR(SExp.CAR(list1))).name.equals("T")) {
			return SExp.CDR(SExp.CAR(list1));
		} else {
			return GETVAL(exp, SExp.CDR(list1));
		}
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp ADDPAIR(SExp pList, SExp argList, SExp list1) {

		try {

		if (SExp.NULL(pList).name.equals("T")) {
			return list1;
		} else {
			return ADDPAIR(SExp.CDR(pList), SExp.CDR(argList),
					SExp.CONS(SExp.CONS(SExp.CAR(pList), SExp.CAR(argList)), list1));
		}
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp ADDDEFINITION(SExp exp) {
		try {
			int len = argLength(SExp.CDR(exp));
			if (len < 3) {
				System.out.println("Error");
				return null;
			} else if (len > 3) {
				System.out.println("Error");
				return null;
			}

			SExp.dList = SExp.CONS(
				SExp.CONS(SExp.CAR(SExp.CDR(exp)),
						SExp.CONS(SExp.CAR(SExp.CDR(SExp.CDR(exp))), SExp.CAR(SExp.CDR(SExp.CDR(SExp.CDR(exp)))))),
						SExp.dList);
		return SExp.dList;
		} catch (Exception e) {
			return null;
		}
	}

	public static int argLength(SExp exp) {
		try {

			if (exp.kind == 3) {
				if (exp.rightChild.kind == 2 && exp.rightChild.name.equals("NIL")) {
					return 1;
				}
				return 1 + argLength(SExp.CDR(exp));
			}

			if (exp.kind == 2) {
				if (exp.name.equals("NIL")) {
					return 0;
				}
				return 0;
			}
			return -2;
		}
 catch (Exception e) {
			return -1;
		}
	}

	public static SExp APPLY(SExp f, SExp x, SExp alist, SExp dlist)
			throws Exception {
		try {
			SExp.defunCheck = true;
		if (((SExp.ATOM(f)).name).equals("T")) {
			if ((SExp.EQ(f, SExp.atomsLeft[2]).name).equals("T")) {
					if ((argLength(x)) > 1) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 1) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.CAR(SExp.CAR(x)));
					}
			} else if ((SExp.EQ(f, SExp.atomsLeft[3]).name).equals("T")) {
					if ((argLength(x)) > 1) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 1) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.CDR(SExp.CAR(x)));
					}
			} else if ((SExp.EQ(f, SExp.atomsLeft[4]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.CONS(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}
			} else if ((SExp.EQ(f, SExp.atomsLeft[5]).name).equals("T")) {
					if ((argLength(x)) > 1) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 1) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.ATOM(SExp.CAR(x)));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[7]).name).equals("T")) {
					if ((argLength(x)) > 1) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 1) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.NULL(SExp.CAR(x)));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[6]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.EQ(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[8]).name).equals("T")) {
					if ((argLength(x)) > 1) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 1) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.INT(SExp.CAR(x)));
					}
			} else if ((SExp.EQ(f, SExp.atomsLeft[9]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.PLUS(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[10]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.MINUS(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[11]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.TIMES(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[12]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.QUOTIENT(SExp.CAR(x),
								SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[13]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.REMAINDER(SExp.CAR(x),
								SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[14]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.LESS(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}

			} else if ((SExp.EQ(f, SExp.atomsLeft[15]).name).equals("T")) {
					if ((argLength(x)) > 2) {
						System.out.println("Error");
						return null;
					} else if ((argLength(x)) < 2) {
						System.out.println("Error");
						return null;
					} else {
						return (SExp.GREATER(SExp.CAR(x), SExp.CAR(SExp.CDR(x))));
					}
			} else {
					SExp dl = SExp.CAR(GETVAL(f, SExp.dList));
					int len = argLength(dl);
					
					if (len < argLength(x)) {
						System.out.println("Error");
						return null;
					} else if (len > argLength(x)) {
						System.out.println("Error");
						return null;
					} else {
						SExp alt = ADDPAIR(SExp.CAR(GETVAL(f, SExp.dList)), x,
								SExp.CONS(SExp.atomsLeft[19], alist));
						return (EVAL(SExp.CDR(GETVAL(f, SExp.dList)), alt, SExp.dList));
					}
			}
		} else {
			System.out.println("Error");
			throw new Exception();
		}

		} catch (Exception e) {
			return null;
		}
	}

	public static SExp checkArgs(SExp paramList) {
		if (SExp.NULL(paramList).name.equals("T")) {
			return SExp.atomsLeft[0];
		} else if (SExp.CAR(paramList).kind == 2) {
			if (SExp.CAR(paramList).name.equals("T")) {
				System.out.println("Error");
				return null;
			} else if (SExp.CAR(paramList).name.equals("NIL")) {
				System.out.println("Error");
				return null;
			} else {
				return checkArgs(SExp.CDR(paramList));
			}
		} else {
			System.out.println("Error");
			return SExp.atomsLeft[1];
		}
	}

}
public class TokenGenerator {

	Stack<String> tokenStack = new Stack();

	public boolean validateExp(String input) {
		Pattern pattern = Pattern.compile("^[-?+?A-Z0-9\\(\\)\\.\\s]*$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}


	public Stack<String> getStack() {
		return tokenStack;
	}
	public void parseInput() {



		while (true) {
			int bracketCount = 0;
			boolean end = false;
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			if (input.equals("(EXIT)")) {
				SExp.dList = SExp.atomsLeft[1];
				System.exit(0);
			}

			while (!end) {
				for (int i = 0; i < input.length(); i++) {
					if (input.charAt(i) == '(') {
						bracketCount++;
					}
					if (input.charAt(i) == ')') {
						bracketCount--;
					}
				}
				if (bracketCount <= 0) {
					end = true;
				} else {
					input = input + " " + in.nextLine();
					bracketCount = 0;
				}
			}

			StringBuffer temp = new StringBuffer();

			boolean validCheck = validateExp(input);

			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == '(' || input.charAt(i) == ')'
						|| input.charAt(i) == '.') {
					temp.append(" ");
					temp.append(input.charAt(i));
					temp.append(" ");
				} else {
					temp.append(input.charAt(i));
			}
			}


			String ip = temp.toString();
			String[] alltokens = ip.split(" ");
			ArrayList<String> fTokens = new ArrayList();

			for (String str : alltokens) {
				if (!str.trim().equals("")) {
					fTokens.add(str);
			}
			}


			Stack<String> s1 = new Stack<String>();
			temp = new StringBuffer();



			for (int i = 0; i < fTokens.size(); i++) {
				s1.add(fTokens.get(fTokens.size() - 1 - i));
			}

			for (String str : fTokens) {
				temp.append(str);
			}

			String str = temp.toString();
			tokenStack = s1;
			boolean valid = validateInput(input);

			if (valid) {
				String finalR = new OutputRoutine().convertToSexp(reverseStack(), "");
				temp = new StringBuffer();

				for (int i = 0; i < finalR.length(); i++) {
					if (finalR.charAt(i) == '(' || finalR.charAt(i) == ')'
							|| finalR.charAt(i) == '.') {
						temp.append(" ");
						temp.append(finalR.charAt(i));
						temp.append(" ");
					} else {

						temp.append(finalR.charAt(i));
					}
				}

				alltokens = temp.toString().split(" ");
				fTokens = new ArrayList();

				for (String str1 : alltokens) {
					if (!str1.trim().equals("")) {
						fTokens.add(str1);
					}
				}
				s1 = new Stack<String>();
				temp = new StringBuffer();


				for (int i = 0; i < fTokens.size(); i++) {
					s1.add(fTokens.get(fTokens.size() - 1 - i));
				}


				SExp out = new TokenHandler().generateSExp(
						new OutputRoutine().reverseStack(s1), new Stack<SExp>());
			
				SExp eval = OtherFuncs.EVAL(out, SExp.atomsLeft[1], SExp.dList);
				if (eval != null) {
					System.out.println(eval.print());
				} else {
					System.out.println("Illegal Expression");
				}
			}
			SExp.defunCheck = false;
		}
	}



	public Stack<String> reverseStack() {
		Stack<String> revToken = new Stack<String>();
		while (!tokenStack.isEmpty()) {
			revToken.push(tokenStack.pop());
		}
		return revToken;
	}




	boolean validateInput(String sexp) {

		boolean validCheck = validateExp(sexp);
		if (validCheck) {


		StringBuffer temp1 = new StringBuffer();

			int bracketCount = 0, dotLevel = 0;
			boolean error = false;
			int whitespaceCount = 0;


		char temp[] = sexp.toCharArray();

			for (char c : sexp.toCharArray()) {
				if (Character.isWhitespace(c)) {
					whitespaceCount++;
			}
			}
			for (int i = 0; i < sexp.length(); i++) {
				if (sexp.charAt(i) == '(' || sexp.charAt(i) == ')'
						|| sexp.charAt(i) == '.') {
					temp1.append(" ");
					temp1.append(sexp.charAt(i));
					temp1.append(" ");
				} else {
					temp1.append(sexp.charAt(i));
			}
			}
			String fInput = temp1.toString();
			String[] alltokens = fInput.split(" ");
			ArrayList<String> finalTokens = new ArrayList();

			for (String str : alltokens) {
				if (!str.trim().equals("")) {
					finalTokens.add(str);
			}
			}
			Stack<String> s1 = new Stack<String>();
			temp1 = new StringBuffer();
			for (int i = 0; i < finalTokens.size(); i++) {
				s1.add(finalTokens.get(finalTokens.size() - 1 - i));
			}
			String ip1[] = new String[s1.size() + whitespaceCount];
			StringBuffer sbuff = new StringBuffer();
			int t = 0;
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] == '(') {
					ip1[t] = "(";
					t++;

				}

				else if (temp[i] == ' ') {
					ip1[t] = " ";
					t++;
				}

				else if (temp[i] == ')') {
					ip1[t] = ")";
					t++;
				}

				else if (temp[i] == '.') {
					ip1[t] = ".";
					t++;
				}

				else {
					int x = i;
					while ((x < temp.length)&& (temp[x] != ' ' && temp[x] != '('
									&& temp[x] != ')' && temp[x] != '.')) {
						sbuff.append(temp[x]);
						x++;
					}

					if (x == temp.length) {
						ip1[t] = sbuff.toString();
						break;
					}

					i = x - 1;
					ip1[t] = sbuff.toString();
					t++;
					sbuff = new StringBuffer();
				}

			}
			int len = ip1.length;

			for (String s : ip1) {


				if (s.length() > 1 && s.length() <= 10) {
					try {
						Integer.parseInt(s);
					} catch (Exception e) {

						if (!Character.isDigit(s.charAt(0))) {
							if (s.indexOf("-") == 0 || s.indexOf("+") == 0) {
								System.out.println("Illegal Expression");
								return false;
							}
						}

						if (Character.isDigit(s.charAt(0))) {
							System.out.println("Illegal Expression");
							return false;
						}

					}
				} else {
					if (s.length() > 10) {
						System.out.println("Illegal Expression");
						return false;
					}
				}

			}
			if (ip1.length == 0) {
				return false;
			}
			if (ip1[0].equals(")") || ip1[0].equals(".")
					|| ip1[len - 1].equals("(") || ip1[len - 1].equals(".")) {
				System.out.println("Illegal Expression");
				error = true;
			} else {
				Stack<String> type = new Stack<String>();
				type.add("List");
				Stack<Integer> dotCount = new Stack<Integer>();
				Stack<Boolean> pushed1 = new Stack<Boolean>();
				boolean pushed = false;

				for (int i = 0; i < len; i++) {
					if (ip1[i].equals("(")) {
						dotCount.push(dotLevel);
						if (i > 0) {
							if (!ip1[i - 1].equals(".")
									&& !ip1[i - 1].equals(" ")
									&& !ip1[i - 1].equals("(")) {
							System.out.println("Illegal Expression");
							error = true;
							break;
							}
						}
						bracketCount++;
						dotLevel = 0;
						pushed1.push(pushed);
						pushed = false;
					} else if (ip1[i].equals(")")) {
						bracketCount--;
						if (bracketCount < 0) {
							System.out.println("Illegal Expression");
							error = true;
							break;
						} else if (bracketCount > 0) {
							try {
								pushed = pushed1.pop();
								dotLevel = dotCount.pop();
								String lastType = type.pop();
							} catch (Exception e) {
							}
							if (ip1[i - 1].equals(".")
									|| ip1[i - 1].equals(" ")) {
								System.out
										.println("Illegal Expression");
								error = true;
								break;
						}
						}
					} else if (ip1[i].equals(".")) {
						if (bracketCount == 0) {
							System.out.println("Illegal Expression");
							error = true;
							break;
						}
						if (pushed == false) {
							type.push("Dot");
							pushed = true;
						}

						if (type.peek().equals("Dot")) {
							if (dotLevel > 0) {
								System.out.println("Illegal Expression");
								error = true;
								break;
							} else if (i < len - 1 && ip1[i + 1].equals(" ")) {
							System.out.println("Illegal Expression");
							error = true;
							break;
						}

							if (i < len - 1 && ip1[i + 1].equals(".")) {
								System.out.println("Illegal Expression");
								error = true;
								break;
							} else if (i < len - 1 && ip1[i - 1].equals("(")) {
								System.out.println("Illegal Expression");
								error = true;
								break;
							} else if (i < len - 1 && ip1[i + 1].equals(")")) {
								System.out.println("Illegal Expression");
								error = true;
								break;
						}
							dotLevel++;

						} else {
							System.out.println("Illegal Expression");
							error = true;
							break;
						}

					} else if (ip1[i].equals(" ")) {
						if (bracketCount == 0) {
							System.out.println("Illegal Expression");
							error = true;
							break;
						}
						if (pushed == false) {
							type.push("List");
							pushed = true;
						}
						if (type.peek().equals("List")) {
							if (i < len - 1 && ip1[i + 1].equals(".")) {
								System.out.println("Illegal Expression");
								error = true;
								break;
							} else if (i < len - 1 && ip1[i + 1].equals(" ")) {
								System.out.println("Illegal Expression");
							error = true;
							break;
							} else if (i < len - 1 && ip1[i - 1].equals("(")) {
							System.out.println("Illegal Expression");
							error = true;
							break;
							} else if (i < len - 1 && ip1[i + 1].equals(")")) {
								System.out.println("Illegal Expression");
							error = true;
							break;
						}
						} else {
							System.out.println("Illegal Expression");
							error = true;
							break;
					}
					}

			}
				if (error == false && bracketCount > 0) {
					System.out.println("Illegal Expression");
					error = true;
				}
			}
			return !error;
		} else {
			System.out.println("Illegal Expression");
			return false;
		}
	}

	public static void main(String[] args) {
		TokenGenerator t1 = new TokenGenerator();
		t1.parseInput();
	}

}

class TokenHandler {

	int kind;
	Stack<String> s1;
	static int depth = 0;


	TokenHandler() {
		TokenGenerator t1 = new TokenGenerator();
		s1 = t1.getStack();
	}

	public void skipToken() {
		s1.pop();
	}
	public int ckToken() {



		if (s1.peek().equals("(")) {
			kind = 1;
			return kind;
		}

		if (s1.peek().equals(")")) {
			kind = 2;
			return kind;
		}

		if (s1.peek().equals(".")) {
			kind = 3;
			return kind;
		}


		try {
			Integer.parseInt(s1.peek());
			kind = 4;
			return kind;

		} catch (Exception e) {
			kind = 5;
			return kind;
		}
	}




	public SExp generateSExp(Stack<String> expStr, Stack<SExp> sexp) {

		Stack<String> temp1 = new Stack();
		if (expStr.size() == 1) {

			String y = expStr.pop();
			SExp temp = null;
			try {
				int x = Integer.parseInt(y);
				temp = new SExp(x);
			} catch (Exception e) {

				String str = y;

				boolean flag1 = false;
				for (int k = 0; k < SExp.atomsConsumed; k++) {
					if (str.equals(SExp.atomsLeft[k].name)) {
						temp = SExp.atomsLeft[k];
						flag1 = true;
					}
				}

				if (!flag1) {
					temp = new SExp(str);
					SExp.atomsLeft[SExp.atomsConsumed++] = temp;

				} else {
				}
			}

			sexp.push(temp);
			return generateSExp(expStr, sexp);
		}

		if (expStr.isEmpty()) {
			return sexp.pop();
		}



		int pos = expStr.indexOf(")");

		int stackSize = expStr.size();

		while (stackSize != (pos + 1)) {

			temp1.push(expStr.pop());
			stackSize--;
		}

		List<String> l1 = expStr.subList(expStr.lastIndexOf("("),
				expStr.indexOf(")") + 1);
		SExp left = null;
		SExp right = null;
		int i = l1.indexOf(".");

		for (int a = 0; a < l1.size(); a++) {
			String str = l1.get(a);
			if ((!str.equals("(") && !str.equals(")") && !str.equals("."))
					&& (a < i)) {
				try {
					Integer.parseInt(str);
					left = new SExp(Integer.parseInt(str));

				} catch (Exception e) {
					boolean flag1 = false;
					for (int k = 0; k < SExp.atomsConsumed; k++) {
						if (str.equals(SExp.atomsLeft[k].name)) {

							left = SExp.atomsLeft[k];
							flag1 = true;
						}
					}

					if (!flag1) {
						left = new SExp(str);
						l1.set(l1.indexOf(str), "-ABC");
						SExp.atomsLeft[SExp.atomsConsumed++] = left;

					} 
				}
			}

			if ((!str.equals("(") && !str.equals(")") && !str.equals("."))
					&& (a > i)) {
				try {
					Integer.parseInt(str);
					right = new SExp(Integer.parseInt(str));

				} catch (Exception e) {

					boolean flag1 = false;
					for (int k = 0; k < SExp.atomsConsumed; k++) {
						if (str.equals(SExp.atomsLeft[k].name)) {
							right = SExp.atomsLeft[k];
							flag1 = true;

						}
					}

					if (!flag1) {
						right = new SExp(str);
						l1.set(l1.indexOf(str), "-ABC");
						SExp.atomsLeft[SExp.atomsConsumed++] = right;
					} 
				}
			}
		}

		SExp result;

		if (left == null && right == null) {
			right = sexp.pop();
			left = sexp.pop();
		}

		else if (left == null) {
			left = sexp.pop();
		}

		else if (right == null) {
			right = sexp.pop();
		}

		result = new SExp(left, right);

		int temp2 = l1.size();
		while (temp2 != 0) {
			expStr.pop();
			temp2--;
		}



		sexp.push(result);
		while (!temp1.isEmpty()) {
			expStr.push(temp1.pop());
		}
		return generateSExp(expStr, sexp);
	}

}

class OutputRoutine {

	boolean completed = false;
	Stack<String> finalTokens;
	Stack<String> finalTokensRev;
	static int depth = 0;
	static int dotDepth = 0;
	Stack<String> typeStack;
	Stack<String> tokenStack;
	String finalStr = null;


	OutputRoutine() {
		TokenGenerator t1 = new TokenGenerator();
		tokenStack = t1.reverseStack();
		typeStack = new Stack();
	}

	public String convertToSexp(Stack<String> tempTokens, String expSoFar) {

		Stack<String> s1 = new Stack();
		Stack<String> temp1 = new Stack();

		StringBuilder sb = new StringBuilder();

		if ((tempTokens.indexOf("(") == -1) && (tempTokens.indexOf(")") == -1)
				&& tempTokens.size() == 1) {

			sb.append(tempTokens.pop());
			finalStr = sb.toString();
			tempTokens.push(finalStr);
			return finalStr;
		}


		int pos = tempTokens.indexOf(")");
		int stackSize = tempTokens.size();
		while (stackSize != (pos + 1)) {
			temp1.push(tempTokens.pop());
			stackSize--;
		}

		List<String> l1 = tempTokens.subList(tempTokens.lastIndexOf("("),
				tempTokens.indexOf(")") + 1);

		if (l1.contains(".")) {

			for (int i = l1.size() - 1; i >= 0; i--) {


				String temp3 = l1.get(i);
				if (temp3 != null) {
					if (temp3.equals(")")) {
						sb.insert(0, temp3);
					}

					else if (temp3.equals(".")) {
						sb.insert(0, temp3);
					}

					else if (temp3.equals("(")) {
						sb.insert(0, temp3);
					}

					else {
						sb.insert(0, temp3);
					}
				}
			}

			finalStr = sb.toString();
		}

		else {

			if (l1.size() == 2) {
				if (l1.get(0).equals("(") && l1.get(1).equals(")")) {
					sb.insert(0, "NIL");
				}
			}
			for (int i = l1.size() - 1; i >= 0; i--) {

				String temp4 = l1.get(i);
				if (temp4 != null) {
					if (temp4.equals(")")) {
					}

					else if (temp4.equals("(")) {
						depth = 0;
					}

					else {
						depth++;
						if (depth == 1) {
							sb.insert(0, "(" + temp4 + ".NIL)");
						} else {
							sb.insert(0, "(" + temp4 + ".");
							sb.append(")");
						}
					}
				}
			}
			finalStr = sb.toString();
		}

		int temp5 = l1.size();
		while (temp5 != 0) {
			tempTokens.pop();
			temp5--;
		}
		tempTokens.push(finalStr);
		while (!temp1.isEmpty()) {
			tempTokens.push(temp1.pop());
		}

		if (tempTokens.size() != 1) {
			return convertToSexp(tempTokens, expSoFar);
		} else {
			if (!completed) {
				completed = true;
				return finalStr;
			}
			return null;
		}

	}


	public Stack<String> reverseStack(Stack<String> s1) {
		Stack<String> temp = new Stack();

		while (!s1.isEmpty()) {
			temp.push(s1.pop());
		}
		return temp;
	}



}


class SExp {

	String name;
	SExp leftChild;
	SExp rightChild;
	int kind;
	int value;
	static int atomsConsumed = 0;
	static StringBuilder sb1 = new StringBuilder();
	static StringBuilder sb2 = new StringBuilder();
	static StringBuilder sb3 = new StringBuilder();
	static int recursionDepth = -1;
	static SExp[] atomsLeft = new SExp[100];
    static boolean defunCheck = false;


	static {
		String[] lockedAtoms = {"T", "NIL", "CAR", "CDR", "CONS", "ATOM", "EQ", "NULL",
				"INT", "PLUS", "MINUS", "TIMES", "QUOTIENT", "REMAINDER",
				"LESS", "GREATER", "COND", "QUOTE", "DEFUN", "MARKER"};

		for (int i = 0; i < lockedAtoms.length; i++) {
			atomsLeft[i] = new SExp(lockedAtoms[i]);
			atomsConsumed++;
		}
		

	}

	static SExp dList = atomsLeft[1];
	SExp aList = atomsLeft[1];

	SExp(int val) {
		kind = 1;
		value = val;
	}

	SExp(String name) {
		kind = 2;
		this.name = name;
	}

	SExp(SExp l, SExp r) {

		kind = 3;
		leftChild = l;
		rightChild = r;
	}

	public String print() {

		if (kind == 3) {
			return "(" + leftChild.print() + "." + rightChild.print() + ")";
		} else if (kind == 1) {
			return Integer.toString(value);
		} else if (kind == 2) {
			return name;
		} else {
			return null;
		}
	}

	public static SExp ATOM(SExp exp1) {

		try {
		String str;
		if (exp1.kind == 1 || exp1.kind == 2) {
			str = "T";
		} else {
			str = "NIL";
		}
		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}
		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp CAR(SExp temp) {
		try {
			if (temp.leftChild.kind == 2) {
				for (int k = 0; k < SExp.atomsConsumed; k++) {
					if ((SExp.atomsLeft[k].name).equals(temp.leftChild.name)) {
						return SExp.atomsLeft[k];
				}
			}
			}

			return temp.leftChild;
		} catch (Exception e) {
			System.out.println("Error");
			return null;
		}
	}

	public static SExp CDR(SExp temp) {
		try {
			if (temp.rightChild.kind == 2) {
				for (int k = 0; k < SExp.atomsConsumed; k++) {
					if ((SExp.atomsLeft[k].name).equals(temp.rightChild.name)) {
						return SExp.atomsLeft[k];
				}
			}
			}
			return temp.rightChild;
		} catch (Exception e) {
			System.out.println("Error");
			return null;
		}
	}

	public static SExp CONS(SExp l, SExp r) {
		try {
		SExp temp = new SExp(l, r);
		return temp;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp EQ(SExp exp1, SExp exp2) {

		try {
		String str;
		if (ATOM(exp1).name.equals("NIL") || ATOM(exp2).name.equals("NIL")) {
				System.out.println("Error");
				return null;
		} else {
			if (exp1.kind != exp2.kind) {
				str = "NIL";
			} else if (exp1.kind == 1 && exp2.kind == 1) {
				if (exp1.value == exp2.value) {
					str = "T";
				} else {
					str = "NIL";
				}
			} else {
				if (exp1.name.equals(exp2.name)) {
					str = "T";
				} else {
					str = "NIL";
				}
			}

		}

		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}

		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp NULL(SExp exp1) {
		try {
		String str;
		if (exp1.name != null && exp1.name.equals("NIL")) {
			str = "T";
		} else {
			str = "NIL";
		}


		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}

		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp PLUS(SExp i1, SExp i2) {
		try {
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			return new SExp(i1.value + i2.value);
		}
		System.out.println("Error");
		return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp MINUS(SExp i1, SExp i2) {
		try {
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			return new SExp(i1.value - i2.value);
		}
		System.out.println("Error");
		return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp QUOTIENT(SExp i1, SExp i2) {
		try {
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			return new SExp(i1.value / i2.value);
		}
		System.out.println("Error");
		return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp TIMES(SExp i1, SExp i2) {
		try {
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			return new SExp(i1.value * i2.value);
		}
		System.out.println("Error");
		return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp REMAINDER(SExp i1, SExp i2) {
		try {
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			return new SExp(i1.value % i2.value);
		}
		System.out.println("Error");
		return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp LESS(SExp i1, SExp i2) {
		try {
		String str;
			if (INT(i1).name.equals("NIL") || INT(i2).name.equals("NIL")) {
				System.out.println("Error");
				return null;
			}
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			if (i1.value < i2.value) {
				str = "T";
			} else {
				str = "NIL";
			}

		} else {
			System.out.println("Error");
			str = "NIL";
		}

		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}
		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}

	}

	public static SExp GREATER(SExp i1, SExp i2) {
		try {
		String str;
			if (INT(i1).name.equals("NIL") || INT(i2).name.equals("NIL")) {
				System.out.println("Error");
				return null;
			}
		if (INT(i1).name.equals("T") && INT(i2).name.equals("T")) {
			if (i1.value > i2.value) {
				str = "T";
			} else {
				str = "NIL";
			}

		} else {
			System.out.println("Error");
			str = "NIL";
		}

		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}
		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static SExp INT(SExp exp) {
		try {
		String str;
		if (exp.kind == 1) {
			str = "T";
		} else {
			str = "NIL";
		}
		for (int k = 0; k < SExp.atomsConsumed; k++) {
			if ((SExp.atomsLeft[k].name).equals(str)) {
				return SExp.atomsLeft[k];
			}

		}
		System.out.println(str);
		return new SExp(str);
		} catch (Exception e) {
			return null;
		}
	}

}

