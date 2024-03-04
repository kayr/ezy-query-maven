package io.github.kayr.ezyquery.maven;

import io.github.kayr.ezyquery.EzySql;
import io.github.kayr.ezyquery.api.Field;
import prod.ProdQuery1;

import java.math.BigDecimal;
import java.util.Vector;

public class CodeUser {

    public static void main(String[] args) throws IllegalAccessException {

        Field<BigDecimal> f1 = ProdQuery1.PROD_QUERY1.F1;
        Field<String> f2 = ProdQuery1.PROD_QUERY1.F2;
        Field<Vector> f3 = ProdQuery1.PROD_QUERY1.F3;

        EzySql e = EzySql.withProvider(null);

        e.from(ProdQuery1.PROD_QUERY1)
          .select(f1, f2, f3)
          .where(f1.eq(3))
          .getQuery().print();


        try {

            Class.forName("test.TestQuery1");
            throw new IllegalAccessException("Should not be able to load TestQuery1");
        } catch (ClassNotFoundException ex) {
            System.out.println("TestQuery1 not found. Good!");
        }

    }
}
