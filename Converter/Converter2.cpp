	#include <iostream>
	#include <string>
	#include <map>
	#include <cmath>
	using namespace std;

	long from10(long number, int base)
	{
		int pos = 1;
		long result = 0;
		while (number != 0) {
			long remainder = number % base;
			result = remainder * pos  + result;
			pos *= 10;
			number = number / base;
		}
		return result;
	}
	char getSymbol(long digit)
	{
		if (digit >= 0 && digit <= 9) {
			return (char)('0' + digit);
		}
		else {
			return (char)('A' + (digit - 10));
		}
	}
	string from10(long number)
	{
		string result = "";
		while (number != 0) {
			long remainder = number % 16;
			result = getSymbol(remainder) + result;
			number = number / 16;
		}
		return result;

	}

	long to10(long number, int base)
	{
		int pos = 0;
		long result = 0;
		while (number != 0) {
			long remainder = number % 10;
			result = (long) (result + remainder * pow(base, (double)pos));
			number = number / 10;
			pos++;
		}
		return result;
	}

	long getValue(char symbol)
	{
		if (symbol >= '0' && symbol <= '9') {
			return symbol - '0';
		}
		else {
			return (symbol - 'A') + 10;
		}
	}

	long to10(string number)
	{
		long result = 0;
		int pos = number.length() - 1;
		for (int i = 0 ; i < number.length();i++) {
			char digit = number[i]; // find the remainder
			result = (long) ( result + getValue(digit) * pow(16.0, pos));// calculate the result
			pos--;
		}
		return result;
	}
	map<char,int> buildRomanMap()
											{
		map<char,int> romanMap;
		romanMap['M'] = 1000;
		romanMap['C'] = 100;
		romanMap['X'] = 10;
		romanMap['I'] = 1;
		romanMap['D'] = 500;
		romanMap['L'] = 50;
		romanMap['V'] = 5;
		return romanMap;

											}
	int fromRoman(string number)
	{
		map<char, int> map = buildRomanMap();
		int result = 0;
		for (int i = 0; i < number.length() - 1;i++) {
			char curr = number[i]; // process the number from left to right
			char next = number[i+1];
			int firstValue = map[curr];
			int secondValue = map[next];
			int value = map[curr];
			if ( firstValue < secondValue) {
				result = result - value ;
			}
			else {
				result = result + value ;

			}

		}
		// At the end of the loop take care of the last character
		int lastIndex = number.length() - 1;
		char lastDigit = number[lastIndex];
		int lastValue = map[lastDigit];
		result = result + lastValue;
		return result;

	}

	int main() {
		// Testing from 10(long, base)
		cout << "Testing from10(long,base)" << endl;
		cout << from10(25,2) << endl; // should print 11001
		cout << from10(25,3) << endl;
		cout << from10(255,7) << endl;
		cout << from10(2526,8) << endl;
		cout << from10(25250,16) << endl;
		// Testing from10(long number)
		cout << "Testing from10(long)" << endl;
		cout << from10(7) << endl;
		cout << from10(74) << endl;
		cout << from10(743) << endl;
		cout << from10(1364) << endl;
		cout << from10(2020) << endl;
		// Testing to10()
		cout << "Testing to10(number, base)" << endl;
		cout << to10(11110,2) << endl;
		cout << to10(11110,3) << endl;
		cout << to10(11110,4) << endl;
		cout << to10(11110,5) << endl;
		cout << to10(11110,6) << endl;
		// Testing to10()
		cout << "Testing to10(String)" << endl;
		cout << to10("7") << endl;
		cout << to10("4A") << endl;
		cout << to10("2E7") << endl;
		cout << to10("554") << endl;
		cout << to10("7E4") << endl;
		// Testing fromRoman(String)
		cout << "Testing fromRoman(String)" << endl;
		cout << fromRoman("CD") << endl; // supposed to be 400
		cout << fromRoman("XL") << endl; // supposed to be 40
		cout << fromRoman("IV") << endl; // supposed to be 4
		cout << fromRoman("CM") << endl; // supposed to be 900
		cout << fromRoman("XC") << endl; // supposed to be 90
		cout << fromRoman("IX") << endl; // supposed to be 9
		cout << fromRoman("XXX") << endl; // supposed to be 30
		cout << fromRoman("CC") << endl; // supposed to be 200
		cout << fromRoman("M") << endl; // supposed to be 1000
		cout << fromRoman("MM") << endl; // supposed to be 2000
		cout << fromRoman("CCC") << endl; // supposed to be 300
		cout << fromRoman("VI") << endl; // supposed to be 6
		cout << fromRoman("XXXIX") << endl; // supposed to be 39
		cout << fromRoman("CCXLVI") << endl; // supposed to be
		cout << fromRoman("DCCLXXXIX") << endl; // supposed to be
		cout << fromRoman("MMCDXXI") << endl; // supposed to be
		cout << fromRoman("MCMXVIII") << endl; // supposed to be 1
		cout << fromRoman("MDCCLXXVI") << endl; // supposed to be 2
		cout << fromRoman("MCMXVIII") << endl; // supposed to be 1
		cout << fromRoman("MCMLIV") << endl; // supposed to be
		cout << fromRoman("MMXIV") << endl; // supposed to be



		return 0;
	}
