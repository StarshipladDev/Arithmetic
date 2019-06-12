
import java.util.*;

/**
 *
 * @author StarshipladDev
 */
public class Etedude10 {
	static char[] operands;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		PrintOver();
	}
	/**
	 * PrintOver is the reccursive method called from main so that it can be recalled for each inut dataset.
	 * PrintOver first takes x numbers, then a target value to amek with those numbers, and a letter 'N' or 'L' to
	 * represent order of operations (The lsat to being on an new line)
	 * Input is read from std.in
	 * PrintOver then calls the revelant order of operations function to provide either the operands required between the given
	 * numbers in order to make the target value number, or returns 'Impossible'
	 * 
	 * 
	 */
	public static void PrintOver() {
		try {
			int[] inputInts;
			int value;
			char type;
			String inputString="";
			Scanner scan = new Scanner(System.in);
			while(scan.hasNext()){
				inputString+=scan.nextLine()+"\n";
			}
			scan=new Scanner(inputString);
			while (scan.hasNextLine()) {
				Scanner lineScan;
				String print;
				int i;
				String inputs = scan.nextLine();
				String outputs = scan.nextLine();
				lineScan = new Scanner(inputs);
				i = 0;
				while (lineScan.hasNext()) {
					lineScan.next();
					i++;
				}
				inputInts = new int[i];
				operands = new char[i];
				lineScan = new Scanner(inputs);
				i = 0;
				while (lineScan.hasNext()) {
					String value1 = lineScan.next();
					int give = Integer.parseInt(value1);
					inputInts[i] = give;
					i++;
				}
				lineScan = new Scanner(outputs);
				value = Integer.parseInt(lineScan.next());
				type = lineScan.next().charAt(0);
				System.out.print(type + " " + value + " ");
				if (type == 'L') {
					if (solve(inputInts, inputInts.length - 1, type, value) == -1) {
						System.out.print(" impossible");
					}
				} 
				else if (type == 'N') {
					if (solveN(inputInts,0,operands.clone(),value) == 0) {
						System.out.print(" impossible");
					}else {
						int ii=0;
						while(ii<inputInts.length-1) {
							System.out.print(inputInts[ii]+" "+operands[ii]+" ");
							ii++;
						}
						System.out.print(inputInts[ii]);
					}
				}
				else {
					System.out.print("Invalid type of Calc");
				}
				System.out.print("\n");
			}

		} catch (Exception e) {
			PrintOver();
		}

	}
	/**
	 * solve is the standard solve method for a given set of numbers.
	 * If 'L' is specified as the order of operations in PrintOver, this function will attempt to reccrusivly
	 * return the required order of operations for all numbers to equal the value.
	 * The function will reccursivly try get all other numbers in a given array to equal the target value both 
	 * subrtacted and divided by the latest number in the input int array.
	 * If either returns true, the program returns true, and prints the relevant operator between the latest and second latest numbers in the array
	 * 
	 * @param Array int[]- The array of numbers given to equal 'value'
	 * @param index int- The current index of 'array' to be subtracted by and divided with 'value' to test whether 'array' can make 'value' at depth 0
	 * @param Type char - The type of order of operations specified in PrintOVer(). Redundant as 'solve' only for 'Left-To-Right'
	 * @param Value int64 - The target value for all given numbers in 'array' to reach
	 * @return An int specifying -1 if the given target value can be reached, or the target value if it can
	 * 
	 * 
	 */
	public static int solve(int[] array, int index, char type, int value) {
		int total = 0;
		int i = 0;
		if (index == 0) {
			if (array[index] == value) {
				System.out.print(array[index]);
				return value;
			} else {
				return -1;
			}
		} else if (value == 0) {
			while (i < index) {
				if (array[i] != 0) {
					return -1;
				}
				i++;
			}
			return value;
		} //If last value, if it equals value, return it
		//if rest of array can equal value-current, return value
		else if (type=='L' && solve(array, index - 1, type, value - array[index]) != -1 ) {
			System.out.print(" + " + array[index]);
			return value;
		} else if (type == 'L') {
			double need = ((double) value / (double) array[index]);
			if (need != (int) ((double) value / (double) array[index])) {
				return -1;
			}
			if (solve(array, index - 1, type, (int) need) != -1) {
				System.out.print(" * " + array[index]);
				return value;

			} else {
				return -1;
			}

		}
		return -1;

	}
	/**
	 * solveN is the standard solve method for a given set of numbers where 'N' is specified as the order type.
	 * If 'N' is specified as the order of operations in PrintOver, this function will attempt to reccrusivly
	 * return the required order of operations for all numbers to equal the value.
	 * The function will reccursivly try get all other numbers in a given array to equal the target value both 
	 * by multiplying all numbers up until the last or a '+' test returns true, then recurrsivly calling the method trying to get atarget value of what's left.
	 * If a check multiplying the current running total by the enxt number, or adding the next number to the running total and makign the runnign total 0 equal the value,
	 * it will return '1' and print the revelant operand between both numbers
	 * 
	 * @param Array int[]- The array of numbers given to equal 'value'
	 * @param index int- The current index of 'array' to be subtracted by and divided with 'value' to test whether 'array' can make 'value' at depth 0
	 * @param Type char - The type of order of operations specified in PrintOVer(). Redundant as 'solve' only for 'Left-To-Right'
	 * @param Value int64 - The target value for all given numbers in 'array' to reach
	 * @return An int specifying -1 if the given target value can be reached, or the target value if it can
	 * 
	 * 
	 */
	public static int solveN(int[] array, int index,char[] opList,int value) {
		int runningTotal=0;
		int total=0;
		int i=0;
		//If one element, check if its that element of value
		if(array.length==1) {
			if(array[0]== value) {
				return 1;
			}else {
				return 0;
			}
		}
		i=0;
		//Count up itterativly
		while(i<=index) {
			//If total got to big, back out
			if (total>value){
				return 0;
			}
			if(runningTotal<0) {
				int f=0;
				boolean keepGoing=false;
				while(f<array.length) {
					if (array[f]==0) {
						keepGoing=true;
					}
					f++;
				}
				if(! keepGoing) {
					return 0;
				}
			}
			else if(i==0) {
				if(index>0) {
					runningTotal=array[0];
				}

			}
			else if(i==array.length-1) {
				if(opList[i-1]=='+') {
					total+=runningTotal;
					total+=array[i];

				}else {
					total+=(runningTotal*array[i]);
				}
				runningTotal=0;


			}
			//Multiplication
			else if(opList[i-1]=='*') {
				runningTotal*=array[i];
			}
			//plus
			else {
				total+=runningTotal;
				runningTotal=array[i];
			}
			i++;
		}
		if(index==array.length-1) {
			if(total==value) {
				operands=opList;
				return 1;
			}else {
				return 0;
			}
		}

		opList[index]='+';
		if(solveN(array,index+1,opList.clone(),value)==0) {
			opList[index]='*';
			if(solveN(array,index+1,opList.clone(),value)==1) {
				return 1;
			}
			return 0;
		}
		return 1;

	}

}