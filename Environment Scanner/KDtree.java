//Maaz Zaidi
//Student Number: 300246507
//Date: December 5, 2022

public class KDtree {

    //KDnode nested class
    public class KDnode {

        //Private node class variables
        private Point3D point;
        private int axis;
        private double value;
        private KDnode left;
        private KDnode right;

        //KDnode constructor
        public KDnode(Point3D pt, int axis) {
            this.point= pt;
            this.axis= axis;
            this.value= pt.get(axis);
            left= right= null;
        }

        // get methods
        public Point3D getPoint3d(){return this.point;}
        public int getAxis(){return this.axis;}
        public double getValue(){return this.value;}
        public KDnode getLeft(){return this.left;}
        public KDnode getRight(){return this.right;}

        // set methods
        public void setPoint3d(Point3D pt){this.point = pt;}
        public void setAxis(int a){this.axis = a;}
        public void setValue(double val){this.value = val;}
        public void setLeft(KDnode leftChild){this.left = leftChild;}
        public void setRight(KDnode rightChild){this.right = rightChild;}
    }

    //Private class variables
    private KDnode root;
    private int size;

    // construct empty tree
    public KDtree() {
        root = null;
        size = 0;
    }

    //Root empty check method
    public boolean isEmpty(){
        return (root==null)?true:false;
    }

    //Add point to tree method
    public void add(Point3D pt){
        if(isEmpty()){
            root = new KDnode(pt, 0);
        }
        else{
            insert(pt, root);
        }
    }

    //Recursive insert for checking point value and insert into tree
    private void insert(Point3D pt, KDnode cursor) throws IllegalStateException{

        //Check for null input
        if(cursor==null) throw new IllegalStateException("Invalid Cursor");

        //Cursor related variable inputs
        KDnode left = cursor.getLeft(), right = cursor.getRight();

        //Check for insert location check (right/left)
        boolean split = split(pt, cursor.getPoint3d(), cursor.getAxis());

        //Main insert recursion
        if(!split){
            //Null check to insert, otherwise recursion
            if(left != null){
                insert(pt, cursor.getLeft());
            }
            else{
                cursor.setLeft(new KDnode(pt, updateAxis(cursor.getAxis())));
            }
        } 
        else {
            //Null check to insert, otherwise recursion
            if(right != null){
                insert(pt, cursor.getRight());
            }
            else{
                cursor.setRight(new KDnode(pt, updateAxis(cursor.getAxis())));
            }
        }

        size++;
    }

    //Boolean method to check for insert side based on pt comparison
    private boolean split(Point3D p1, Point3D p2, int axis){return (p1.get(axis) > p2.get(axis))?true:false;}

    //Axis looper
    private int updateAxis(int keyAxis){return (keyAxis == 2)?0:keyAxis+1;}

    // Get methods

    public KDnode getRoot(){return root;}
    public int getSize(){return size;}
        
}