/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eva.assignment;
import Jama.Matrix;
import java.lang.Math.*;
import java.util.Scanner;


public class EVAAssignment {
 
//fuction to display Matrix with results
public static void displayMatrix(int matrix[][]) {
   for (int i = 0; i < 3; i++) { 
        for (int j = 0; j < 3; j++) 
            System.out.print( matrix[i][j] + " "); 
        System.out.println();
    }
}
    
//Challenge 1
static void fillMissing1(int matrix[][]) 
{ 
    int sum ;
    sum=matrix[0][0]+matrix[0][1]+matrix[0][2];
    matrix[2][0]=sum - (matrix[0][0]+matrix[1][0]);
    matrix[1][1]=sum - (matrix[0][2]+matrix[2][0]);
    matrix[1][2]=sum - (matrix[1][0]+matrix[1][1]);
    matrix[2][1]=sum - (matrix[0][1]+matrix[1][1]);
    matrix[2][2]=sum - (matrix[0][2]+matrix[1][2]);
    displayMatrix(matrix);
}

//Challenge 2
/*
Matrix = a   7  16
         15  x  c
         b   d  11

eqution1: a+7+16=3x ... a-3x=-33
eqution2: a+11=2x   ... a-2x=-11
solve 2 eqution ... 1  -3  -23
                    1  -2  -11
*/
public static void fillMissing2(int matrix[][])  {
    
    int a=1 , b=-3 , c=1 , d=-2 , e=-23 , f=-11;
    int det = ((a) * (d) - (b) * (c));  
    int x = ((d) * (e) - (b) * (f)) / det;
    int y = ((a) * (f) - (c) * (e)) / det;
    
    matrix[0][0]=x;
    matrix[1][1]=y;
    int sum = matrix[0][0]+matrix[0][1]+matrix[0][2];
    matrix[2][0]=sum - (matrix[0][0]+matrix[1][0]);
    matrix[2][1]=sum - (matrix[2][0]+matrix[2][2]);
    matrix[1][2]=sum - (matrix[1][0]+matrix[1][1]);
    displayMatrix(matrix);
      
}

//Challange 3
/*
Matrix = a   0   0
         31  x   15
         b   41  c

equation 1 : 31+x+15=3x ... x=23
equation 2 : a+b+31=69  .... a+b=38
equation 3 : b+c+41=69  .... b+c=28
equation 4 : a+c+23=69  .... a+c=46
solve equation 2,3,4 :  1  1  0  38
                        1  0  1  46
                        0  1  1  28
*/
public static void fillMissing3(int matrix[][]){
    
    matrix[1][1]=(matrix[1][0]+matrix[1][2])/2;
    int sum = 3*matrix[1][1];
    double[][] lhsArray = {{1, 1, 0}, {1, 0, 1}, {0, 1, 1}};
        double[] rhsArray = {38, 46, 28};
        //Creating Matrix Objects with arrays
        Matrix lhs = new Matrix(lhsArray);
        Matrix rhs = new Matrix(rhsArray, 3);
        //Calculate Solved Matrix
        Matrix ans = lhs.solve(rhs);
        matrix[0][0]=(int) Math.round(ans.get(0, 0));
        matrix[2][0]=(int) Math.round(ans.get(1, 0));
        matrix[2][2]=(int) Math.round(ans.get(2, 0));
        matrix[0][1]=sum - (matrix[1][1]+matrix[2][1]);
        matrix[0][2]=sum - (matrix[0][1]+matrix[0][0]);
        displayMatrix(matrix);
    
}

//Challange 4
/*
3 by 3 magic squares are a vector space with these three basis elements
1  1  1      0  1 -1     -1  1  0
1  1  1     -1  0  1      1  0 -1
1  1  1      1 -1  0      0 -1  1

introduce 3 variables a, b, c that represent the contribution from each of the 3 basis elements
For example, given your example grid you'll have:

a + b - c = 18
a - b - c = 28

Which immediately gives 2b = 10 or b=-5. And a-c = 23, or c=a-23

The space of solutions looks like this:
23    2a-28 a+5
2a-18 a     18
a-5   28    2a-23

Assume that a=30
*/
public static int fillMissing4(int matrix[][] , int x){
    matrix[1][1]=x;
    int sum = 3*x;
    matrix[1][0]=(2*x)-18;
    matrix[2][0]=x-5;
    matrix[0][0]=sum - (matrix[1][0]+matrix[2][0]);
    matrix[2][2]=(2*x)-23;
    matrix[0][1]=(2*x)-28;
    matrix[0][2]=x+5;
    if(isPositiveNumbers(matrix)){
        if(isMagicSquare(matrix )){
            displayMatrix(matrix);
        }    
    }
    else{
        System.out.println("The number you entered make magic square has negative numbers or zeros");
    }
    return sum;
}

//Challenge 5
public static void fillMissing5(int matrix[][] ){
    int x=1;
    while(x>0) {
        matrix[1][1]=x;
        matrix[0][0]=23;
        int sum = 3*x;
        matrix[1][0]=(2*x)-18;
        matrix[2][0]=x-5;
        matrix[2][2]=(2*x)-23;
        matrix[0][1]=(2*x)-28;
        matrix[0][2]=x+5;
        
       if(isPositiveNumbers(matrix)){
           if(isMagicSquare(matrix )){
                System.out.println("the smallest 3 sums");
                displayMatrix(matrix);
                System.out.println("sum = "+sum);
                break;
           }    
       }
       x++;
    }
    x++;
    int y=x+2;
    for(;x<y;x++){
        System.out.println("..................");
        int sum = fillMissing4(matrix ,  x);
        System.out.println("sum = "+sum);
    }
    

}

//function to check if numbers of matrix are positive or not
public static boolean isPositiveNumbers(int mat[][]){
    int n = 3;
    for (int i = 0; i < n; i++) { 
            for (int j = 0; j < n; j++) { 
                if (mat[i][j] <= 0 ) 
                    return false; 
            } 
        } 
    return true;   
}

//function to check if the sum of all rows , columns and diagonals are equal
public static boolean isMagicSquare(int mat[][]) 
    { 
        int n = 3;
        int sum = 0,sum2=0;  
        for (int i = 0; i < n; i++) 
            sum = sum + mat[i][i]; 

        for (int i = 0; i < n; i++) 
            sum2 = sum2 + mat[i][n-1-i]; 
  
        if(sum!=sum2)  
            return false; 

        for (int i = 0; i < n; i++) { 
            int rowSum = 0; 
            for (int j = 0; j < n; j++) 
                rowSum += mat[i][j]; 

            if (rowSum != sum) 
                return false; 
        } 

        for (int i = 0; i < n; i++) { 
            int colSum = 0; 
            for (int j = 0; j < n; j++) 
                colSum += mat[j][i]; 
 
            if (sum != colSum) 
                return false; 
        } 
    return true; 
 } 
  

    
public static void main(String[] args) {
        
    int sq[][] = { 
        { 12, 17, 10 }, 
        { 11, 0, 0 }, 
        { 0, 0, 0 } 
    }; 
        
    int sq2[][] = { 
        { 0, 7, 16 }, 
        { 15, 0, 0 }, 
        { 0, 0, 11 } 
    };

    int sq3[][] = { 
        { 0, 0, 0 }, 
        { 31, 0, 15 }, 
        { 0, 41, 0 } 
    };
            
    int sq4[][] = { 
        { 0, 0, 0 }, 
        { 0, 0, 18 }, 
        { 0, 28, 0 } 
    };
    
    System.out.println("Challenge 1" );
    fillMissing1(sq); 
    System.out.println("=======================");
    System.out.println( "Challenge 2" );
    fillMissing2(sq2);
    System.out.println("=======================");
    System.out.println( "Challenge 3" );
    fillMissing3(sq3);
    System.out.println("=======================");
    System.out.println( "Challenge 4" );
    Scanner scanner = new Scanner( System.in );
    System.out.println( "Input an integer to be middle in magic square" );
    int input = scanner.nextInt();
    fillMissing4(sq4,input);
    System.out.println("=======================");
    System.out.println( "Challenge 5" );
    fillMissing5(sq4);
    
    }        
}
    

