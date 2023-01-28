
package entidades;

public class Libro {
    private int id;
    private String isbn;
    private String titulo;
    private String autores;
    private Tema tema;
    private Idioma idioma;
    private int aniopublicacion;

    public Libro() {
        tema = new Tema();
        idioma = new Idioma();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAniopublicacion(int aniopublicacion) {
        this.aniopublicacion = aniopublicacion;
    }

    public int getAniopublicacion() {
        return aniopublicacion;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getAutores() {
        return autores;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    /*public void setEstado(int estado) {
        this.estado = estado;
    }
    
    */

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Tema getTema() {
        return tema;
    }
    
    @Override
    public String toString(){
        return String.format("{ID: %s, Título: %s, ISBN: %s, Autor: %s, Año de publicación: %s, Idioma: %s}", 
                getId(), getTitulo(), getIsbn(), getAutores(), getAniopublicacion(), getIdioma().getDescripcion());
    }
    
}
