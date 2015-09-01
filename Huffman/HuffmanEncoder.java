import java.util.*;

/**
 * Created by Yufan on 7/16/2015.
 */
public class HuffmanEncoder {
    Map<Character, String> codeMap;
    Node root;
    private class Node implements Comparable<Node>{
        Character aChar;
        int frequency;
        Node left,right;


        private Node(Character aChar, int frequency){
            this.aChar = aChar;
            this.frequency = frequency;
        }
        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }
    public HuffmanEncoder(){
        codeMap = new HashMap<>();
        root = null;
    }
    public Map<Character, String> getMap(){
        return codeMap;
    }

    public String encode(String text){
        char newChar;
        Map<Character, Integer> map = new HashMap<>();

        for(int i = 0; i < text.length();i++){
            newChar = text.charAt(i);
            if(map.containsKey(newChar)){
                int oldFrequency = map.get(newChar);
                oldFrequency++;
                map.put(newChar, oldFrequency);
            } else {
                map.put(newChar, 1);
            }
        }
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        //priorityQueue.add(new Node(null,0));
        for(Character toPut: map.keySet()){
            priorityQueue.add(new Node(toPut,map.get(toPut)));
        }
        //make the borrom up tree
        //root = new Node(null,0);
        while (priorityQueue.size() > 1) {
            Node newNode = new Node(null, 0);
            Node firstNode = priorityQueue.poll();
            Node secondNode = priorityQueue.poll();
            if(firstNode.aChar !=null && secondNode.aChar ==null){
                newNode.right = firstNode;
                newNode.left = secondNode;
            } else{
                newNode.left =  firstNode;
                newNode.right = secondNode;
            }

            newNode.frequency = newNode.left.frequency + newNode.right.frequency;
            root = newNode;
            priorityQueue.add(newNode);
        }

        //make the encodeMap
        StringBuilder charCode = new StringBuilder();
        if(root != null){
            visitNode(root, charCode);
        }
        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < text.length();i++){
            newChar = text.charAt(i);
            toReturn.append(codeMap.get(newChar));
        }
        return toReturn.toString();
    }


    public String decode(Map<Character, String> map, String text){
        StringBuilder toReturn = new StringBuilder();
        StringBuilder aChar = new StringBuilder();
        for(int i = 0; i < text.length();i++){
            char newChar = text.charAt(i);
            aChar.append(newChar);
            for(Map.Entry<Character,String> entry: map.entrySet()){
                if(aChar.toString().equals(entry.getValue())){
                    toReturn.append(entry.getKey());
                    aChar.setLength(0);
                }
            }
        }
        return toReturn.toString();
    }

    public static void main(String [] args){
        HuffmanEncoder hf = new HuffmanEncoder();
        String code;
        String toCode;
        toCode = "GGCATTTAGGGGCCATX";
        code = hf.encode(toCode);
        System.out.println(code);
        String deCode;
        deCode = hf.decode(hf.getMap(),code);
        System.out.println(deCode);
        if(deCode.equals(toCode)){
            System.out.println("Your Huffman Encoder works!!!");
        }else{
            System.out.println("There is something wrong!!!");
        }
    }

    private void visitNode(Node node, StringBuilder charCode ) {
        if(((node.left != null && node.left.frequency == 0)|| node.left==null)
                && ((node.right != null && node.right.frequency == 0)|| node.right==null)
                && node.frequency == 0){
            return;
        }
        if(node.left != null && node.left.frequency != 0 && valid(node.left) ) {
            charCode.append("0");
            visitNode(node.left, charCode);
        }
        if(node.right != null && node.right.frequency != 0 && valid(node.right)) {
            charCode.append("1");
            visitNode(node.right, charCode);
        }
        if(node.left == null && node.right == null && node.aChar == null){
            return;
        }
        if(node.left == null && node.right == null && node.aChar != null) {
            //System.out.println(node.aChar);
            codeMap.put(node.aChar, charCode.toString());
            node.aChar = null;
            node.frequency = 0;
            charCode.setLength(0);
            visitNode(root, charCode);
        }
    }
    private boolean valid(Node node){
        if(node == null ||node.frequency == 0){
            return false;
        }
        if(node.aChar!= null && node.frequency != 0){
            return true;
        }
        if(((node.left != null && node.left.frequency == 0)|| node.left==null)
            && ((node.right != null && node.right.frequency == 0)|| node.right==null)) {
            node.frequency = 0;
            return false;
        }
        return true;
    }
}
