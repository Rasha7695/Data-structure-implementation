package a4q2;
public abstract class Shape{

	public float getArea(){
		return 0;
	}
}

class Square extends Shape {
	
    public int width;

	// -------------------------------------------------------------
    public Square(int width) {
        this.width = width;
    }		
	// -------------------------------------------------------------
    /**
     * Return the area of an square node
     */
	public float getArea()
	{
		return width*width;
	}

	@Override
	public String toString()
	{
		return("Type Square, width="+width);		
	}
}

class Circle extends Shape {

    public int radius;

	// -------------------------------------------------------------
    public Circle(int r) {
    	radius = r;
    }		
	
	@Override
	public float getArea()
	{
		return (float) (Math.PI*Math.pow(radius, 2));
	}	
	// -------------------------------------------------------------
	@Override
	public String toString()
	{		
		return("Type Circle, radius="+radius);			
	}
}

class Triangle extends Shape {

    public int height, base;

	// -------------------------------------------------------------
    public Triangle(int h, int b) {
        height = h;
        base = b;
    }	
	// -------------------------------------------------------------	
    /**
     * Return the area of a triangular node
     */    
	@Override
	public float getArea()
	{
		return height*base/2;
	}
	@Override
	public String toString()
	{
		return("Type Triangle, height="+height+", base="+base);		
	}
}


