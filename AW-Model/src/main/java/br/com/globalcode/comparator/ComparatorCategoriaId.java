package br.com.globalcode.comparator;

import br.com.globalcode.model.Categoria;

import java.util.Comparator;


public class ComparatorCategoriaId implements Comparator {

  public int compare(Object o1, Object o2) {

    Categoria cat1 = (Categoria) o1;
    Categoria cat2 = (Categoria) o2;

    return compareId(cat1, cat2);

  }

  private int compareId(Categoria cat1, Categoria cat2) {

    Integer idCat1 = cat1 != null ? cat1.getId() : null;
    Integer idCat2 = cat2 != null ? cat2.getId() : null;

    if (idCat1 == null || idCat2 == null) {
      return 0;
    } else if (idCat1 == null) {
      return -1;
    } else if (idCat2 == null) {
      return +1;
    } else {
      return idCat1.compareTo(idCat2);
    }

  }

}
