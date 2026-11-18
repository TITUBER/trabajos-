public class EmpPer extends Empleado {

    int ant;

    public EmpPer(String nom, String ape, String ced, int hij, int ant) {
        super(nom, ape, ced, hij);
        this.ant = ant;
    }

    public double sue() {
        return 20000 + hij * 1000 + ant * 1000;
    }
}