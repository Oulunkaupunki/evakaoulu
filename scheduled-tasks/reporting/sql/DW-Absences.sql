SELECT
    ab.child_id as lapsenID,
    ab.date as poissaolonpvm,
    ab.absence_type as poissaolontyyppi,
    ab.category as poissaolonkategoria,
    pl.type as sijoitustyyppi
FROM absence ab, placement pl
WHERE :date_val::DATE - INTERVAL '3 months' <= ab.date
AND ab.child_id = pl.child_id
AND pl.start_date <= now()
AND pl.end_date >= now()
ORDER BY ab.date, ab.child_id;