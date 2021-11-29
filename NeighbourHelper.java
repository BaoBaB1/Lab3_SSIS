public class NeighbourHelper {


    public boolean isAlive(int row, int col, boolean[][] fullCurrentGeneration, int sz) {
        boolean[] neighbours = new boolean[sz];

        int index = 0;
        if (row == 0) {
            // top left
            if (col == 0) {
                neighbours[index++] = fullCurrentGeneration[0][1];
                neighbours[index++] = fullCurrentGeneration[1][0];
                neighbours[index++] = fullCurrentGeneration[1][1];
                neighbours[index++] = fullCurrentGeneration[0][sz - 1];
                neighbours[index++] = fullCurrentGeneration[1][sz - 1];
                neighbours[index++] = fullCurrentGeneration[sz - 1][0];
                neighbours[index++] = fullCurrentGeneration[sz - 1][1];
                neighbours[index] = fullCurrentGeneration[sz - 1][sz - 1];
            }
            // top right
            else if (col == sz - 1) {
                neighbours[index++] = fullCurrentGeneration[0][sz - 2];
                neighbours[index++] = fullCurrentGeneration[1][sz - 1];
                neighbours[index++] = fullCurrentGeneration[1][sz - 2];
                neighbours[index++] = fullCurrentGeneration[sz - 1][sz - 2];
                neighbours[index++] = fullCurrentGeneration[sz - 1][sz - 1];
                neighbours[index++] = fullCurrentGeneration[0][0];
                neighbours[index++] = fullCurrentGeneration[1][0];
                neighbours[index] = fullCurrentGeneration[sz - 1][0];
                // 1st row
            } else {
                neighbours[index++] = fullCurrentGeneration[row][col - 1];
                neighbours[index++] = fullCurrentGeneration[row][col + 1];
                neighbours[index++] = fullCurrentGeneration[row + 1][col - 1];
                neighbours[index++] = fullCurrentGeneration[row + 1][col];
                neighbours[index++] = fullCurrentGeneration[row + 1][col + 1];
                neighbours[index++] = fullCurrentGeneration[sz - 1][col - 1];
                neighbours[index++] = fullCurrentGeneration[sz - 1][col];
                neighbours[index] = fullCurrentGeneration[sz - 1][col + 1];
            }
        }
        else if (row == sz - 1) {
            // bottom left
            if (col == 0) {
                neighbours[index++] = fullCurrentGeneration[sz - 2][0];
                neighbours[index++] = fullCurrentGeneration[sz - 2][1];
                neighbours[index++] = fullCurrentGeneration[sz - 1][1];
                neighbours[index++] = fullCurrentGeneration[sz - 1][sz - 1];
                neighbours[index++] = fullCurrentGeneration[sz - 2][sz - 1];
                neighbours[index++] = fullCurrentGeneration[0][0];
                neighbours[index++] = fullCurrentGeneration[0][1];
                neighbours[index] = fullCurrentGeneration[0][sz - 1];
            }
            // bottom right
            else if (col == sz - 1) {
                neighbours[index++] = fullCurrentGeneration[sz - 1][sz - 2];
                neighbours[index++] = fullCurrentGeneration[sz - 2][sz - 2];
                neighbours[index++] = fullCurrentGeneration[sz - 2][sz - 1];
                neighbours[index++] = fullCurrentGeneration[0][sz - 2];
                neighbours[index++] = fullCurrentGeneration[0][sz - 1];
                neighbours[index++] = fullCurrentGeneration[sz - 2][0];
                neighbours[index++] = fullCurrentGeneration[sz - 1][0];
                neighbours[index] = fullCurrentGeneration[0][0];
                // last row
            } else {
                neighbours[index++] = fullCurrentGeneration[row][col - 1];
                neighbours[index++] = fullCurrentGeneration[row][col + 1];
                neighbours[index++] = fullCurrentGeneration[row - 1][col - 1];
                neighbours[index++] = fullCurrentGeneration[row - 1][col];
                neighbours[index++] = fullCurrentGeneration[row - 1][col + 1];
                neighbours[index++] = fullCurrentGeneration[0][col - 1];
                neighbours[index++] = fullCurrentGeneration[0][col];
                neighbours[index] = fullCurrentGeneration[0][col + 1];
            }
        }
        // first col except top left and bottom left
        else if (col == 0) {
            neighbours[index++] = fullCurrentGeneration[row - 1][col];
            neighbours[index++] = fullCurrentGeneration[row + 1][col];
            neighbours[index++] = fullCurrentGeneration[row - 1][col + 1];
            neighbours[index++] = fullCurrentGeneration[row][col + 1];
            neighbours[index++] = fullCurrentGeneration[row + 1][col + 1];
            neighbours[index++] = fullCurrentGeneration[row - 1][sz - 1];
            neighbours[index++] = fullCurrentGeneration[row][sz - 1];
            neighbours[index] = fullCurrentGeneration[row + 1][sz - 1];
        }
        // last col except top right and bottom right
        else if (col == sz - 1) {
            neighbours[index++] = fullCurrentGeneration[row - 1][col];
            neighbours[index++] = fullCurrentGeneration[row + 1][col];
            neighbours[index++] = fullCurrentGeneration[row - 1][col - 1];
            neighbours[index++] = fullCurrentGeneration[row][col - 1];
            neighbours[index++] = fullCurrentGeneration[row + 1][col - 1];
            neighbours[index++] = fullCurrentGeneration[row][0];
            neighbours[index++] = fullCurrentGeneration[row + 1][0];
            neighbours[index] = fullCurrentGeneration[row - 1][0];
        }
        // all others
        else {
            neighbours[index++] = fullCurrentGeneration[row - 1][col];
            neighbours[index++] = fullCurrentGeneration[row - 1][col - 1];
            neighbours[index++] = fullCurrentGeneration[row - 1][col + 1];
            neighbours[index++] = fullCurrentGeneration[row][col - 1];
            neighbours[index++] = fullCurrentGeneration[row][col + 1];
            neighbours[index++] = fullCurrentGeneration[row + 1][col - 1];
            neighbours[index++] = fullCurrentGeneration[row + 1][col];
            neighbours[index] = fullCurrentGeneration[row + 1][col + 1];
        }

        int aliveNeighbours = 0;
        for (int i = 0; i < sz; i++) {
            if (neighbours[i]) {
                aliveNeighbours++;
            }
        }

        if (!fullCurrentGeneration[row][col]) {
            return aliveNeighbours == 3;
        }
        else {
            return aliveNeighbours == 3 || aliveNeighbours == 2;
        }


    }

}
