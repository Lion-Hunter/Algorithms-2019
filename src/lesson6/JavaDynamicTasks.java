package lesson6;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int k = first.length();
        int l = second.length();
        StringBuffer result = new StringBuffer();

        if (l == 0 || k == 0) return "";

        char[] x = first.toCharArray();
        char[] y = second.toCharArray();

        int[][] lcs = new int[k + 1][l + 1];

        for (int i = 0; i <= k; i++) { lcs[i][0] = 0; }
        for (int j = 1; j <= l; j++) { lcs[0][j] = 0; }

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= l; j++) {
                if (x[i - 1] == y[j - 1]) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
                }
            }
        } //O(n^2)

        writeLCS(k, l, x, lcs, result);
        return result.toString();
    } //сложность O(n^2) , ресурсоемкость O(n*m) = O(n^2) где n, m - длины строк

    private static void writeLCS(int i, int j, char[] x, int[][] lcs, StringBuffer result) {
        if (i == 0 || j == 0) return;
        if (lcs[i][j] == lcs[i - 1][j - 1] + 1
                && lcs[i][j] != lcs[i - 1][j] && lcs[i][j] != lcs[i][j- 1]) {
            result.insert(0, x[i - 1]);
            writeLCS(i - 1, j -1, x, lcs, result);
        } else {
            if (lcs[i - 1][j] > lcs[i][j - 1]) writeLCS(i - 1, j, x, lcs, result);
            else writeLCS(i, j - 1, x, lcs, result);
        }
    } //O(n)

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(inputName), StandardCharsets.UTF_8));
        String str = reader.readLine();
        if (str.matches("[^ 0-9]")) throw new IllegalArgumentException();

        List<Integer> lastStr = new ArrayList<>();
        List<Integer> currStr = new ArrayList<>();

        while (str != null) {
            if (str.matches("[^ 0-9]")) throw new IllegalArgumentException();

            for (int i = 0; i < str.split(" +").length; i++) {
                if (currStr.size() <= i) { currStr.add(new Integer(str.split(" +")[i])); }
                else { currStr.set(i, new Integer(str.split(" +")[i])); }
            }

            int minNeighbour;

            for (int i = 0; i < currStr.size(); i++) {
                minNeighbour = 0;

                if (i > 0 && lastStr.isEmpty()) minNeighbour = currStr.get(i - 1);
                else if (!lastStr.isEmpty() && i == 0) minNeighbour = lastStr.get(i);
                else if (i > 0) {
                    minNeighbour = currStr.get(i - 1);
                    minNeighbour = Math.min(lastStr.get(i), minNeighbour);
                    minNeighbour = Math.min(lastStr.get(i - 1), minNeighbour);
                }

                currStr.set(i, minNeighbour + currStr.get(i));
            }

            for (int i = 0; i < currStr.size(); i++) {
                if (lastStr.size() <= i) { lastStr.add(currStr.get(i)); }
                else lastStr.set(i, currStr.get(i));
            }

            str = reader.readLine();
        }

        return lastStr.get(lastStr.size() - 1);
    } //Трудоемкость O(n^2), ресурсоемкость O(n), где n - длина строк

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
