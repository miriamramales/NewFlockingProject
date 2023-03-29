public class Vector {
    
    private double x;

    private double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getVectorX(){
        return this.x;
    }

    public double getVectorY(){
        return this.y;
    }

    public String toString() {
        return "["+x+","+y+"]";
    }

    public Vector sub(Point p, Point other){
        double x = p.x - other.x;
        double y = p.y - other.y;
        return new Vector(x,y);
    }

    public Vector normalize(){
        double angle = Math.atan2(this.y, this.x);
        double x = 2 * Math.cos(angle);
        double y = 2 * Math.sin(angle);
        return new Vector(x,y);
    }

    public Vector div(double d){
        double x = this.x / d;
        double y = this.y / d;
        return new Vector(x,y);
    }

    public Vector add(Point p){
        double x = this.x + p.x;
        double y = this.y + p.y;
        return new Vector(x,y);
    }

    public Vector weightSlide(double n){
        double x = this.x * (n/100);
        double y = this.y * (n/100);
        return new Vector(x,y);
    }
    




}
