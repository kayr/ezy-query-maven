-- ## static: StaticQuery
SELECT
    maq.f1 as f1_double,
    maq.f2 as f2_string,
    maq.f3 as f3_int,
    maq.f4 as f4_date
FROM
    m_wallet maw
WHERE maw.id = :walletId and maw.id = :walletId

-- ## dynamic: Dynamic
SELECT
    maq.f1 as f1_double,
    maq.f2 as f2_string,
    maq.f3 as f3_int,
    maq.f4 as f4_date
FROM
    m_wallet maw
WHERE maw.id = :walletId and maw.id = :walletId