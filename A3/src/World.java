
/*
 * CS5011 A3 Starter code
 * This enum class holds the various boards to be played
 *
 * author Mun See Chang
 *
 *
 * TEST: 6 boards
 * SMALL (S): 5x5, 10 boards
 * MEDIUM (M): 7x7, 10 boards
 * LARGE (L): 9x9, 10 boards
 *
 * Two hints are given at indexes [0][0] and [length/2][length/2]
 *
 *
 */

public enum World {

	TEST0(new char[][] { //map from the spec
			{ '0', '0', '1', 't', '1' },
			{ '1', '1', '0', '1', '1' },
			{ '1', 't', '2', '1', '0' },
			{ '1', '2', '3', 't', '1' },
			{ 't', '2', 't', '2', '1' },
	}),

	TEST1(new char[][] { //from lectures
			{'1','1','t'},
			{'t','1','1'},
			{'t','2','0'}
	}),

	TEST2(new char[][] { //from lectures
			{'2','t','1'},
			{'t','2','1'},
			{'t','2','0'}
	}),

	TEST3(new char[][] {{'0', '1', 't'}, {'0', '0', '1'}, {'0', '0', '0'}}),
	TEST4(new char[][] {{'2', 't', 't'}, {'t', '2', '2'}, {'1', '1', '0'}}),
	TEST5(new char[][] {{'1', 't', 't'}, {'1', '3', '3'}, {'1', 't', 't'}}),


	//----------SMALL-----------

	SMALL0(new char[][] {{'0', '0', '1', 't', '2'}, {'0', '0', '0', '2', 't'}, {'1', '1', '1', '2', '2'}, {'2', 't', '2', 't', 't'}, {'1', 't', '2', '2', 't'}}),
	SMALL1(new char[][] {{'0', '1', 't', 't', 't'}, {'1', '1', '1', '2', '2'}, {'2', 't', '1', '0', '0'}, {'t', '2', '2', '1', '0'}, {'1', '1', '1', 't', '1'} }),
	SMALL2(new char[][] {{'0', '1', 't', '2', '0'}, {'0', '0', '2', 't', '1'}, {'2', '1', '0', '1', '1'}, {'t', 't', '1', '0', '0'}, {'t', '3', '1', '0', '0'}}),
	SMALL3(new char[][] {{'0', '0', '1', '2', 't'}, {'1', '0', '1', 't', '3'}, {'t', '1', '0', '2', 't'}, {'2', '3', '1', '0', '1'}, {'1', 't', 't', '1', '0'}}),
	SMALL4(new char[][] {{'0', '1', 't', '2', '0'}, {'0', '0', '3', 't', '2'}, {'1', '2', '2', 't', 't'}, {'2', 't', 't', '3', '2'}, {'1', 't', 't', 't', '1'}}),
	SMALL5(new char[][] {{'0', '1', '2', 't', '1'}, {'1', '2', 't', '2', '1'}, {'1', 't', '3', '3', '1'}, {'1', '1', '2', 't', 't'}, {'t', '1', '0', '1', '2'}}),
	SMALL6(new char[][] {{'0', '1', 't', 't', '2'}, {'1', '1', '2', '5', 't'}, {'2', 't', '4', 't', 't'}, {'3', 't', 't', 't', 't'}, {'t', 't', 't', '4', 't'}}),
	SMALL7(new char[][] {{'0', '1', '3', 't', '1'}, {'1', '1', 't', 't', '3'}, {'t', '1', '1', '4', 't'}, {'t', '4', '1', '1', 't'}, {'2', 't', 't', '1', '1'}}),
	SMALL8(new char[][] {{'0', '0', '1', '1', '0'}, {'1', '1', '1', 't', '2'}, {'2', 't', '1', '3', 't'}, {'t', '2', '1', '2', 't'}, {'1', '1', '0', '1', 't'}}),
	SMALL9(new char[][] {{'0', '1', '1', '0', '0'}, {'1', '1', 't', '1', '0'}, {'t', '1', '1', '2', '1'}, {'t', '2', '0', '1', 't'}, {'t', '2', '0', '0', '1'}}),

	//----------MEDIUM-----------

	MEDIUM0(new char[][] {{'0', '1', 't', '2', 't', '2', '1'}, {'2', '1', '2', '3', '3', '3', 't'}, {'t', 't', '2', 't', 't', 't', '2'}, {'t', 't', '2', '1', '2', '2', '1'}, {'t', '3', '1', '0', '0', '0', '0'}, {'2', '2', '1', '1', '1', '2', '1'}, {'1', 't', '2', 't', '2', 't', 't'}}),
	MEDIUM1(new char[][] {{'0', '0', '1', 't', '1', '0', '0'}, {'0', '1', '2', '3', '3', '2', '1'}, {'1', '1', 't', 't', 't', 't', 't'}, {'t', '1', '2', '4', '3', '2', '2'}, {'t', '2', '1', 't', 't', '1', '0'}, {'t', '3', '0', '2', '3', '1', '0'}, {'2', 't', '1', '1', 't', '1', '0'}}),
	MEDIUM2(new char[][] {{'0', '0', '0', '2', 't', '1', '0'}, {'1', '1', '0', '1', 't', '2', '0'}, {'1', 't', '2', '0', '2', '2', '0'}, {'1', '4', 't', '1', '1', 't', '1'}, {'1', 't', 't', '2', '0', '1', '1'}, {'1', '2', '2', '1', '0', '0', '0'}, {'1', 't', '1', '0', '0', '0', '0'}}),
	MEDIUM3(new char[][] {{'0', '0', '1', '1', '0', '1', '1'}, {'0', '0', '2', 't', '1', '1', 't'}, {'0', '1', '2', 't', '2', '0', '1'}, {'0', '1', 't', '2', '1', '0', '0'}, {'1', '1', '1', '1', '0', '1', '1'}, {'2', 't', '2', '1', '0', '1', 't'}, {'1', 't', '3', 't', '1', '0', '1'}}),
	MEDIUM4(new char[][] {{'0', '2', 't', 't', '1', '1', 't'}, {'0', '1', 't', '4', '1', '0', '1'}, {'1', '1', '2', 't', '1', '0', '0'}, {'1', 't', '2', '1', '1', '0', '0'}, {'1', '3', 't', '2', '1', '0', '0'}, {'2', 't', '2', '2', 't', '3', '1'}, {'1', 't', '2', '0', '2', 't', 't'}}),
	MEDIUM5(new char[][] {{'0', '0', '1', '1', '0', '0', '0'}, {'0', '0', '1', 't', '1', '1', '1'}, {'0', '0', '0', '1', '1', '2', 't'}, {'1', '0', '0', '0', '0', '1', 't'}, {'t', '2', '0', '0', '1', '1', '1'}, {'3', 't', '2', '1', '2', 't', '1'}, {'1', 't', '3', 't', '2', 't', '2'}}),
	MEDIUM6(new char[][] {{'0', '2', 't', '2', '3', 't', '1'}, {'0', '1', 't', '3', 't', 't', '2'}, {'1', '1', '2', '1', '2', 't', '2'}, {'t', '3', 't', '1', '0', '1', '1'}, {'2', 't', '3', '3', '1', '0', '0'}, {'0', '1', '3', 't', 't', '1', '0'}, {'0', '0', '1', 't', '3', '1', '0'}}),
	MEDIUM7(new char[][] {{'0', '1', 't', '3', 't', 't', '1'}, {'0', '1', '2', '2', 't', '4', '1'}, {'0', '1', 't', '1', '2', 't', '1'}, {'1', '2', '2', '1', '0', '1', '1'}, {'2', 't', 't', '2', '1', '1', '0'}, {'1', 't', '4', 't', '2', 't', '2'}, {'0', '1', '1', '1', '1', '2', 't'}}),
	MEDIUM8(new char[][] {{'0', '0', '0', '1', '1', '2', 't'}, {'0', '0', '0', '1', 't', '2', 't'}, {'1', '1', '0', '1', '2', '1', '1'}, {'2', 't', '2', '2', 't', '1', '0'}, {'2', 't', '3', 't', '2', '2', '1'}, {'t', '3', '2', '2', '2', '1', 't'}, {'1', '2', 't', '2', 't', '1', '1'}}),
	MEDIUM9(new char[][] {{'0', '0', '1', '1', '1', 't', '1'}, {'1', '1', '2', 't', '1', '1', '1'}, {'t', '3', 't', '2', '1', '0', '0'}, {'t', 't', '2', '1', '1', '2', '1'}, {'1', '2', '1', '1', '2', 't', 't'}, {'1', '1', '1', '1', 't', '2', '2'}, {'t', '2', 't', '1', '1', '1', '0'}}),

	//----------LARGE-----------

	LARGE0(new char[][] {{'0', '0', '0', '1', 't', 't', 't', 't', '1'}, {'0', '0', '0', '1', '4', 't', '3', '3', '2'}, {'1', '1', '0', '1', 't', 't', '3', '2', 't'}, {'1', 't', '1', '0', '2', 't', '4', 't', '2'}, {'0', '2', '2', '0', '0', '3', 't', '2', '1'}, {'0', '1', 't', '1', '0', '2', 't', '2', '0'}, {'0', '0', '1', '1', '0', '1', 't', '2', '0'}, {'1', '2', '1', '0', '0', '0', '1', '1', '0'}, {'1', 't', 't', '1', '0', '0', '0', '0', '0'}}),
	LARGE1(new char[][] {{'0', '0', '1', '1', '0', '0', '0', '1', '1'}, {'0', '0', '1', 't', '2', '2', '1', '1', 't'}, {'0', '1', '2', '2', '2', 't', 't', '1', '1'}, {'0', '1', 't', 't', '1', '2', '3', '2', '1'}, {'0', '0', '2', '3', '1', '1', 't', '2', 't'}, {'0', '0', '1', 't', '1', '0', '1', '1', '1'}, {'0', '0', '0', '2', '3', '1', '1', '1', '0'}, {'0', '0', '0', '1', 't', 't', '2', 't', '1'}, {'0', '0', '0', '0', '1', '2', '1', '1', '1'}}),
	LARGE2(new char[][] {{'0', '0', '0', '0', '0', '1', '2', 't', '2'}, {'1', '1', '0', '0', '0', '1', 't', '3', 't'}, {'1', 't', '1', '0', '0', '0', '2', '2', '1'}, {'1', '1', '1', '0', '0', '0', '1', 't', '1'}, {'t', '2', '1', '0', '0', '0', '0', '2', '2'}, {'1', '2', 't', '2', '0', '1', '1', '1', 't'}, {'0', '0', '2', 't', '1', '1', 't', '2', '2'}, {'0', '0', '0', '1', '1', '0', '2', '3', 't'}, {'0', '0', '0', '0', '0', '0', '1', 't', '2'}}),
	LARGE3(new char[][] {{'0', '0', '0', '1', '1', '0', '1', '1', '0'}, {'1', '1', '1', '2', 't', '2', '1', 't', '1'}, {'2', 't', '2', 't', '4', 't', '1', '1', '1'}, {'2', 't', '3', '1', '2', 't', '3', '0', '0'}, {'t', '5', 't', '1', '0', '2', 't', '1', '0'}, {'2', 't', 't', '2', '1', '2', '3', '2', '0'}, {'1', '2', '2', '2', '2', 't', 't', 't', '1'}, {'2', 't', '1', '1', 't', '2', '2', '2', '1'}, {'t', '2', '1', '0', '1', '1', '0', '0', '0'}}),
	LARGE4(new char[][] {{'1', '2', 't', '1', '1', '1', '1', 't', '1'}, {'2', 't', '3', '2', '1', 't', '1', '1', '1'}, {'t', '3', '3', 't', '1', '1', '1', '1', '1'}, {'t', '4', 't', '2', '1', '0', '0', '2', 't'}, {'1', '2', 't', '2', '0', '0', '0', '1', 't'}, {'0', '0', '1', '2', '1', '0', '0', '0', '1'}, {'1', '1', '0', '1', 't', '1', '1', '1', '0'}, {'1', 't', '1', '0', '2', '2', '1', 't', '1'}, {'0', '1', '1', '0', '1', 't', '1', '1', '1'}}),
	LARGE5(new char[][] {{'0', '0', '0', '0', '1', 't', '1', '0', '0'}, {'0', '0', '0', '0', '0', '1', '2', '1', '0'}, {'1', '0', '0', '0', '0', '0', '1', 't', '2'}, {'t', '1', '0', '0', '0', '0', '0', '3', 't'}, {'1', '2', '1', '0', '0', '0', '1', '2', 't'}, {'0', '1', 't', '1', '1', '2', '2', 't', '2'}, {'0', '0', '1', '1', '1', 't', 't', '2', '1'}, {'1', '0', '0', '0', '0', '1', '2', '1', '0'}, {'t', '1', '0', '0', '0', '0', '0', '0', '0'}}),
	LARGE6(new char[][] {{'0', '2', 't', '1', '0', '1', '2', 't', '1'}, {'0', '1', 't', '2', '1', '2', 't', '2', '1'}, {'1', '1', '2', '1', '2', 't', '4', '2', '0'}, {'t', '2', 't', '1', '1', 't', 't', 't', '1'}, {'2', '2', '1', '1', '0', '1', '2', '2', '1'}, {'3', 't', '2', '1', '0', '0', '1', '1', '0'}, {'t', 't', '3', 't', '1', '0', '1', 't', '1'}, {'1', '3', '2', '1', '2', '1', '0', '1', '1'}, {'0', '1', 't', '1', '1', 't', '1', '0', '0'}}),
	LARGE7(new char[][] {{'0', '1', '1', '1', 't', '2', 't', '3', '1'}, {'1', '1', 't', '1', '2', '2', '2', 't', 't'}, {'t', '1', '1', '1', '2', 't', '2', '3', 't'}, {'1', '1', '0', '0', '1', 't', '4', 't', '2'}, {'0', '1', '1', '0', '0', '3', 't', '3', '1'}, {'0', '1', 't', '1', '1', '2', 't', 't', '1'}, {'2', '1', '1', '1', '1', 't', '2', '2', '1'}, {'t', 't', '1', '0', '0', '1', '1', '0', '0'}, {'1', '2', '1', '0', '0', '0', '0', '0', '0'}}),
	LARGE8(new char[][] {{'0', '0', '0', '0', '0', '1', '2', '1', '0'}, {'0', '1', '1', '0', '0', '2', 't', 't', '1'}, {'0', '1', 't', '1', '0', '1', 't', '3', '1'}, {'1', '2', '2', '1', '0', '0', '1', '1', '0'}, {'1', 't', 't', '2', '0', '0', '0', '0', '0'}, {'1', '2', '4', 't', '1', '0', '0', '1', '1'}, {'t', '2', 't', '3', '1', '0', '0', '1', 't'}, {'2', '2', '3', 't', '1', '0', '1', '1', '1'}, {'t', '2', 't', '2', '1', '0', '1', 't', '1'}}),
	LARGE9(new char[][] {{'0', '0', '0', '0', '0', '0', '0', '2', 't'}, {'0', '0', '0', '0', '0', '0', '1', '2', 't'}, {'1', '1', '0', '0', '0', '1', '2', 't', '2'}, {'1', 't', '2', '1', '0', '1', 't', '2', '1'}, {'0', '1', '2', 't', '2', '0', '1', '1', '0'}, {'0', '1', '2', '3', 't', '1', '0', '0', '0'}, {'0', '2', 't', 't', '2', '1', '1', '2', '1'}, {'0', '1', 't', '4', '3', '1', '2', 't', 't'}, {'0', '0', '1', '2', 't', 't', '2', 't', '3'}}),

	;

	public char[][] map;

	World(char[][] map) {
		this.map = map;
	}
}