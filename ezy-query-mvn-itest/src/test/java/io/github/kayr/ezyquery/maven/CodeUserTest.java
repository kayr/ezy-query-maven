package io.github.kayr.ezyquery.maven;

import io.github.kayr.ezyquery.EzySql;
import io.github.kayr.ezyquery.api.Field;
import io.github.kayr.ezyquery.parser.QueryAndParams;
import org.junit.jupiter.api.Assertions;
import prod.AllQueries;
import prod.AllQueries.Dynamic;
import prod.ProdQuery1;
import test.TestQuery1;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
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

    @org.junit.jupiter.api.Test
    void testProdDynamicQueryEzSql() {

        Dynamic Q = AllQueries.dynamic();
        Field<BigDecimal> f1 = Q.F1;
        Field<String> f2 = Q.F2;
        Field<Integer> f3 = Q.F3;
        Field<LocalDate> f4 = Q.F4;

        String sql = e.from(Q)
                .select(f1, f2, f3)
                .setParam(Dynamic.PARAMS.WALLET_ID, "the-wallet-id")
                .getQuery().getSql();

        System.out.println(sql);

        Assertions.assertEquals("SELECT \n" +
                "  maq.f1 as \"f1\", \n" +
                "  maq.f2 as \"f2\", \n" +
                "  maq.f3 as \"f3\"\n" +
                "FROM m_wallet maw\n" +
                "WHERE (maw.id = ? AND maw.id = ?) AND (1 = 1)\n" +
                "LIMIT 50 OFFSET 0", sql);

    }

    @org.junit.jupiter.api.Test
    void testProdStaticQueryEzSql() {

        AllQueries.StaticQuery Q = AllQueries.staticQuery();


        QueryAndParams query = Q.walletId("the-wallet-id").getQuery();
        String sql = query.getSql();

        System.out.println(sql);

        Assertions.assertEquals("SELECT\n" +
                "    maq.f1 as f1_double,\n" +
                "    maq.f2 as f2_string,\n" +
                "    maq.f3 as f3_int,\n" +
                "    maq.f4 as f4_date\n" +
                "FROM\n" +
                "    m_wallet maw\n" +
                "WHERE maw.id = ? and maw.id = ?", sql);

        Assertions.assertEquals(2, query.getParams().size());
        Assertions.assertEquals(Arrays.asList("the-wallet-id", "the-wallet-id"), query.getParams());


    }


}