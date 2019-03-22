/*
Point Data Type
Shane Byrne
* */


/*
This is the data type used to store in the information for each point.
It operates somewhat like a stack as you can only add and remove from the top.
This is very useful for the top grids, and while it's not as simple to use for the bottom grids
it's workable.
* */

public class PointDataType {
    private Pip[] array; //array of pips used to store the pip information of the point
    private int top;
    //counter that counts the number of actual pips in the point. placeholder pips are not counted
    private int playerPipCounter;
    private int columnIndex; //each point has a column index for its respective grid that's stored here
    private char pipColour;
    private int inverse;

    public PointDataType(){
        this(15);
    }

    public PointDataType(int capacity){
        array = new Pip[capacity];
        top = 0;
        playerPipCounter = 0;
    }

    //adds a pip to the top of the point
    public void push(Pip Node){
        if(top == array.length) {
            throw new StackFullException();
        }
        array[top] = Node;
        top++;
    }

    //removes and returns the pip at the top of the point
    public Pip pop(){
        if(isEmpty()) throw new StackEmptyException();
        Pip temp = array[top-1];
        array[top-1] = null;
        top--;
        return temp;
    }

    //returns top pip
    public Pip peek(){
        if(isEmpty()) throw new StackEmptyException();
        return array[top-1];
    }

    //returns size
    public int size() {
        return top;
    }

    //returms false if empty
    public boolean isEmpty() {
        return top == 0;
    }

    //increments playerPipCounter
    public void pipCounterAdder(){
        playerPipCounter++;
    }

    //decrements playerPipCounter
    public void pipCounterDecrementer(){
        playerPipCounter--;
    }

    //returns playerPipCounter
    public int getPlayerPip(){
        return playerPipCounter;
    }

    //sets columnIndex
    public void setColumnIndex(int index){
        columnIndex = index;
    }

    //returns columnIndex
    public int getColumnIndex(){
        return columnIndex;
    }

    public void setPipColour(char pipColour) {
        this.pipColour = pipColour;
    }

    public char getPipColour() {
        return pipColour;
    }

    public void setInverse(int inverse){
        this.inverse = inverse;
    }

    public int getInverse() {
        return inverse;
    }
}
