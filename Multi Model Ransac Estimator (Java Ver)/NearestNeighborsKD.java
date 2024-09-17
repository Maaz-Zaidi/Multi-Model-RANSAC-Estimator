//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;

import java.util.ArrayList;

public class NearestNeighborsKD implements NN{

    //Instantiates class variables
    private KDtree tree;
    private List<Point3D> n;

    //Constructor
    public NearestNeighborsKD(List<Point3D> points){
        tree = new KDtree();
        for (Point3D p: points){
            tree.add(p);
        }
    }

    //Initiator for rangeQuery to search for neighbors
    public List<Point3D> rangeQuery(double Eps, Point3D Q){
        List<Point3D> N = new ArrayList<Point3D>();
        rangeQuery(Eps, Q, N, tree.getRoot());
        n = N;
        return n;
    }

    //Recursive method starting from tree, to search for neighbors inside the KDTree
    private void rangeQuery(double Eps, Point3D Q, List<Point3D> N, KDtree.KDnode node){

        //Check for null node
        if(node==null){return;}

        //Check for neighbor validity
        if(Q.distance(node.getPoint3d()) <= Eps){
            N.add(node.getPoint3d());
        }

        //Continue check based on range being either right, left or both
        int range = circularRange(Q, node.getPoint3d(), node.getAxis(), Eps);
        if(range != 0){
            rangeQuery(Eps, Q, N, node.getRight());
        }
        if(range != 1){
            rangeQuery(Eps, Q, N, node.getLeft());
        }
    }

    //Method to check range existence for the relative nodes
    //0 represetns left or down, 1 represents right or up, 2 represents both
    private int circularRange(Point3D p1, Point3D p2, int axis, double eps){
        if(p1.get(axis) + eps < p2.get(axis)){
            return 0;
        }
        else if(p1.get(axis) - eps > p2.get(axis)){
            return 1;
        }
        else{
            return 2;
        }
    }

    //Getter
    public int getSize(){return n.size();}

}