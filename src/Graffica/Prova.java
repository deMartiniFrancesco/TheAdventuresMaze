package Graffica;



import javax.swing.*;
import java.awt.GridLayout; //imports GridLayout library

    public class Prova {

        JFrame frame=new JFrame(); //creates frame
        JLabel[][] grid; //names the grid of buttons

        public Prova(int width, int length){ //constructor
            frame.setLayout(new GridLayout(width,length)); //set layout
            grid=new JLabel[width][length]; //allocate the size of grid
            for(int y=0; y<length; y++){
                for(int x=0; x<width; x++){
                    grid[x][y]= new JLabel("("+x+","+y+")"); //creates new button
                    frame.add(grid[x][y]); //adds button to grid
                }
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); //sets appropriate size for frame
            frame.setVisible(true); //makes frame visible
        }
        public static void main(String[] args) {
            new Prova(10,10);//makes new ButtonGrid with 2 parameters
        }
    }
