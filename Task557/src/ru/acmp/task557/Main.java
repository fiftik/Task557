package ru.acmp.task557;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static int count = 0;
	public static int p;

	public static void main(String[] args) throws IOException {
		matrixMultiplication();
	}

	private static void matrixMultiplication() throws IOException {

		String myJarPath = Main.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		String dirPath = new File(myJarPath).getParent();
		dirPath = dirPath + "\\files";

		Scanner scanner = new Scanner(new File(dirPath + "\\INPUT.TXT"));
		int m = scanner.nextInt();
		int n = scanner.nextInt();
		int line = scanner.nextInt() - 1;
		int column = scanner.nextInt() - 1;
		p = scanner.nextInt();
		int answer = 0;
		int[][] c, lastMatr;
		int[][][] allMatrix = new int[m - 1][n][n];
		lastMatr = new int[n][n];
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					allMatrix[i][j][k] = scanner.nextInt();
				}
			}
		}
		for (int j = 0; j < n; j++) {
			for (int k = 0; k < n; k++) {
				lastMatr[j][k] = scanner.nextInt();
			}
		}
		scanner.close();
		c = mult(allMatrix);
		answer = sumRowCol(line, column, c, lastMatr);
		FileWriter writer = new FileWriter(dirPath + "\\OUTPUT.TXT");
		writer.write(String.valueOf(answer));
		writer.close();
	}

	private static int[][] mult(int[][]... args) {
		int n = args[0].length;
		final int[][][] m1 = { new int[n][n] };
		final int[][][] m2 = { new int[n][n] };

		if (args.length > 2) {
			int half = args.length / 2;
			half = args.length % 2 == 0 ? half : ++half;

			int[][][] c1 = new int[half][n][n];
			int[][][] c2 = new int[args.length - half][][];

			for (int i = 0; i < half; i++) {
				c1[i] = args[i];
			}
			for (int i = 0; i < args.length - half; i++) {
				c2[i] = args[i + half];
			}

			m1[0] = mult(c1);
			if (!(args.length - half == 1)) {
				m2[0] = mult(c2);
			} else {
				return multiply(m1[0], c2[0]);
			}
			return multiply(m1[0], m2[0]);
		}
		return multiply(args[0], args[1]);
	}

	private static int[][] multiply(int[][] a, int[][] b) {
		int n = a.length;
		int[][] c = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c[i][j] = sumRowCol(i, j, a, b);
			}
		}
		return c;
	}

	private static int sumRowCol(int row, int col, int[][] m1, int[][] m2) {
		int res = 0;
		for (int i = 0; i < m1.length; i++) {
			res += m1[row][i] * m2[i][col];
			count++;
		}
		if (res >= p) {
			res %= p;
		}
		return res;
	}
}
