public class EmpPorHor extends Empleado {

    int hor;

    public EmpPorHor(String nom, String ape, String ced, int hij, int hor) {
        super(nom, ape, ced, hij);
        this.hor = hor;
    }

    public double sue() {
        return hor * 100;
    }
}