package convert;

import java.text.DecimalFormat;

public class EnglishNumberToWords {
	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };

	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private EnglishNumberToWords() {
	}

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " hundred" + soFar;
	}

	public static String convert(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "0000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int crores = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int lakhs = Integer.parseInt(snumber.substring(3, 5));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(5, 7));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(7, 10));

		String tradCrores;
		switch (crores) {
		case 0:
			tradCrores = "";
			break;
		case 1:
			tradCrores = convertLessThanOneThousand(crores) + " crore ";
			break;
		default:
			tradCrores = convertLessThanOneThousand(crores) + " crore ";
		}
		String result = tradCrores;

		String tradLakhs;
		switch (lakhs) {
		case 0:
			tradLakhs = "";
			break;
		case 1:
			tradLakhs = convertLessThanOneThousand(lakhs) + " lakh ";
			break;
		default:
			tradLakhs = convertLessThanOneThousand(lakhs) + " lakh ";
		}
		result = result + tradLakhs;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "one thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	public static String convertNumber(long number) {
		String finalresult = null;

		String snumber = Long.toString(number);
		if (snumber.length() > 10) {

			int digitscount = snumber.length() - 10;
			String beforenumbers = snumber.substring(0, digitscount).concat("000");
			String afternumbers = snumber.substring(digitscount, snumber.length());
			long extraNumber = Long.parseLong(beforenumbers);
			long extraNumber1 = Long.parseLong(afternumbers);

			String result = convert(extraNumber);
			String result1 = convert(extraNumber1);
			finalresult = result + result1;
		} else
			finalresult = convert(number);

		return finalresult;

	}

	/**
	 * testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(EnglishNumberToWords.convertNumber(11912345678912L));
		System.out.println(EnglishNumberToWords.convertNumber(11912));
		System.out.println(EnglishNumberToWords.convertNumber(456662345678912L));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(0));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(1));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(16));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(100));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(1184));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(20001));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(222112));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(800));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(801));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(1316));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(1000000));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(2000000));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(3000200));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(700000));
		System.out.println("*** " + EnglishNumberToWords.convert(9000000));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(9001000));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(300000000L));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(2147483647));
		System.out.println("*** " + EnglishNumberToWords.convertNumber(999999999999L));

	}
	 /*
    eleven lakh ninety one thousand two hundred thirty four crore fifty six lakh seventy eight thousand nine hundred twelve
eleven thousand nine hundred twelve
four crore fifty six lakh sixty six thousand two hundred thirty four crore fifty six lakh seventy eight thousand nine hundred twelve
*** zero
*** one
*** sixteen
*** one hundred
*** one thousand one hundred eighty four
*** twenty thousand one
*** two lakh twenty two thousand one hundred twelve
*** eight hundred
*** eight hundred one
*** one thousand three hundred sixteen
*** ten lakh 
*** twenty lakh 
*** thirty lakh two hundred
*** seven lakh 
*** ninety lakh 
*** ninety lakh one thousand 
*** thirty crore 
*** two hundred fourteen crore seventy four lakh eighty three thousand six hundred forty seven
*** ninety nine thousand nine hundred ninety nine crore ninety nine lakh ninety nine thousand nine hundred ninety nine

    **/
}

