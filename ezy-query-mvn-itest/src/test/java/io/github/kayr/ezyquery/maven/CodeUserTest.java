package io.github.kayr.ezyquery.maven;

import io.github.kayr.ezyquery.EzySql;
import io.github.kayr.ezyquery.api.Field;
import org.junit.jupiter.api.Assertions;
import prod.ProdQuery1;
import test.TestQuery1;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

class CodeUserTest {

    EzySql e = EzySql.withProvider(null);

    @org.junit.jupiter.api.Test
    void testTestQueryIsAccessible() {

        Field<BigDecimal> f1 = TestQuery1.TEST_QUERY1.F1;
        Field<String> f2 = TestQuery1.TEST_QUERY1.F2;
        Field<List> f3 = TestQuery1.TEST_QUERY1.F3;
        Field<Clock> f4 = TestQuery1.TEST_QUERY1.F4;

        String sql = e.from(TestQuery1.TEST_QUERY1)
                       .select(f1, f2, f3, f4)
                       .getQuery().getSql();

        System.out.println(sql);

        Assertions.assertEquals("SELECT \n" +
                                  "  maq.f1 as \"f1\", \n" +
                                  "  maq.f2 as \"f2\", \n" +
                                  "  maq.f3 as \"f3\", \n" +
                                  "  maq.f4 as \"f4\"\n" +
                                  "FROM m_wallet maw\n" +
                                  "WHERE (1 = 1)\n" +
                                  "LIMIT 50 OFFSET 0", sql);

    }

    @org.junit.jupiter.api.Test
    void testProdQueryIsAccessible() {

        Field<BigDecimal> f1 = ProdQuery1.PROD_QUERY1.F1;
        Field<String> f2 = ProdQuery1.PROD_QUERY1.F2;
        Field<Vector> f3 = ProdQuery1.PROD_QUERY1.F3;
        Field<LocalDate> f4 = ProdQuery1.PROD_QUERY1.F4;

        String sql = e.from(ProdQuery1.PROD_QUERY1)
                       .select(f1, f2, f3)
                       .getQuery().getSql();

        System.out.println(sql);

        Assertions.assertEquals("SELECT \n" +
                                  "  maq.f1 as \"f1\", \n" +
                                  "  maq.f2 as \"f2\", \n" +
                                  "  maq.f3 as \"f3\"\n" +
                                  "FROM m_wallet maw\n" +
                                  "WHERE (1 = 1)\n" +
                                  "LIMIT 50 OFFSET 0", sql);

    }

}