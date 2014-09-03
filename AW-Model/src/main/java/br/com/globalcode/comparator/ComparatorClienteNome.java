package br.com.globalcode.comparator;

import br.com.globalcode.model.Cliente;

import java.util.Comparator;


public class ComparatorClienteNome implements Comparator {

  public int compare(Object o1, Object o2) {

    Cliente p1 = (Cliente) o1;
    Cliente p2 = (Cliente) o2;

    return compareNome(p1, p2);

  }

  private int compareNome(Cliente p1, Cliente p2) {

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
