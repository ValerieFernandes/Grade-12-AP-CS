import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;

/**
 * A program to decompress a .MZIP type compressed file
 * @author Valerie Fernandes
 */
public class FileDecompressor {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner (System.in);

        File compressedFile;
        String fileName;
        do {
            System.out.println("Please enter the valid name of your compressed .MZIP file");
            fileName = input.nextLine(); //Get name of compressed file
            compressedFile = new File(fileName);
        }while((!compressedFile.exists()) || (!fileName.substring(fileName.length() - 5).equals(".MZIP"))); //If file does not exist or is not an MZIP try a new file name

        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(compressedFile));

        byte[] bytes = new byte[(int) compressedFile.length()]; //read the bytes of the file into a byte array
        int nextByte;
        int index = 0;
        while((nextByte = fileInput.read()) != -1){
            bytes[index] = (byte) nextByte;
            index++;
        }

        String[] lineInfo = trimLine(bytes); //trim the original file name from the byte array
        String decompressedFileName = lineInfo[0];
        decompressedFileName = decompressedFileName.substring(0, decompressedFileName.indexOf(".") + 1) + decompressedFileName.substring(decompressedFileName.indexOf(".") + 1).toLowerCase();
        bytes = Arrays.copyOfRange(bytes, Integer.parseInt(lineInfo[1]), bytes.length); //shrink the byte array to no longer include file name

        lineInfo = trimLine(bytes); //trim the string representing the huffman tree
        String treeString = lineInfo[0];
        bytes = Arrays.copyOfRange(bytes, Integer.parseInt(lineInfo[1]), bytes.length);
        HuffmanTree integerTree = new HuffmanTree(treeString);

        int extraBits = Character.getNumericValue(bytes[0]); //trim the line containing the integer of extra bits
        bytes = Arrays.copyOfRange(bytes, 2, bytes.length);

        createDecompressedFile(bytes, extraBits, integerTree, decompressedFileName);

        System.out.println("Your file \"" + decompressedFileName +"\" has been decompressed");
    }

    /**
     * this function determines the first line in a byte array by looping until a newline character is reached
     * @param bytes a byte array representing the bytes from which the line is trimmed
     * @return a String array representing the info of the first lin (content and index)
     */
    public static String[] trimLine(byte[] bytes){

        String lineText = "";
        String[] lineInfo = new String [2];

        for(int i=0; i<bytes.length; i++){
            if((char)bytes[i] == '\n'){ //on newline character the line has ended
                lineInfo[0] = lineText;
                lineInfo[1] = Integer.toString(i + 1); //the index of the end of the line
                break;
            }
            lineText = lineText + (char) bytes[i];
        }
        return lineInfo;
    }

    /**
     * This function creates a file given information from it's MZIP file
     * @param bytes a byte[] representing the bytes containing file bytes information
     * @param extraBits an int representing how many bits of the last byte are unused
     * @param integerTree a HuffmanTree representing the paths to each byte that can appear in the file
     * @param fileName the name of the file which the decompressed data is written into
     * @throws Exception
     */
    public static void createDecompressedFile(byte[] bytes, int extraBits,  HuffmanTree integerTree, String fileName) throws Exception{
        File decompressedFile = new File(fileName);
        FileOutputStream fileOutput = new FileOutputStream(decompressedFile);
        HuffmanTreeNode currentNode = integerTree.getRoot();

        for(int i=0; i<bytes.length; i++){
            for(int j=7; j>=0; j--){

                if((i == bytes.length - 1) && (j == extraBits - 1)){
                    break;

                }else{
                    if (( bytes[i] & (int) Math.pow(2,j))  > 0){
                        currentNode = currentNode.getRight();
                    }else{
                        currentNode = currentNode.getLeft();
                    }

                    if(currentNode.getItem() != null){
                        fileOutput.write(currentNode.getItem());
                        currentNode = integerTree.getRoot();
                    }
                }
            }
        }
    }
}
