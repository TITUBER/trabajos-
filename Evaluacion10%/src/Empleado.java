public abstract class Empleado {

    String nom;
    String ape;
    String ced;
    int hij;

    public Empleado(String nom, String ape, String ced, int hij) {
        this.nom = nom;
        this.ape = ape;
        this.ced = ced;
        this.hij = hij;
    }

    public abstract double sue();

    public String getNom() {
        return nom;
    }

    public String getApe() {
        return ape;
    }

    public String getCed() {
        return ced;
    }

    public int getHij() {
        return hij;
    }
}
