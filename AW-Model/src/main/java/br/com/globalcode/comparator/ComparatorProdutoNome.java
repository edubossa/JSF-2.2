package br.com.globalcode.comparator;

import java.util.Comparator;

import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.Produto;

public class ComparatorProdutoNome implements Comparator {

  public int compare(Object o1, Object o2) {

    Produto p1 = (Produto) o1;
    Produto p2 = (Produto) o2;

    return compareNome(p1, p2);

  }

  private int compareNome(Produto p1, Produto p2) {

    if (p1.getNome() == null && p2.getNome() == null) {
      return 0;
    } else if (p1.getNome() == null) {
      return -1;
    } else if (p2.getNome() == null) {
      return +1;
    } else {
      return p1.getNome().compareTo(p2.getNome());
    }

  }

}
