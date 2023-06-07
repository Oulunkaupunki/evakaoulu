SELECT
    d.name as toimintayksikkö,
    d.opening_date as toimintayksikön_alkupvm,
    d.closing_date as toimintayksikön_loppupvm,
    d.dw_cost_center as dw_kustannuspaikka,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
          AND p.start_date <= current_date AND current_date <= p.end_date
    ) as toimintayksikön_lapsimäärä,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
        AND p.start_date < date_trunc('month', current_date) AND date_trunc('month', current_date) < p.end_date --Edellisen kuun viimeisen päivän mukaan
    ) as toimintayksikön_lapsimäärä_ed_kuun_lopussa,
    dg.name as ryhmä,
    dg.start_date as ryhmän_alkupvm,
    dg.end_date as ryhmän_loppupvm,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
          AND dgp.start_date <= current_date AND current_date <= dgp.end_date
    ) as ryhmän_lapsimäärä,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
        AND dgp.start_date < date_trunc('month', current_date) AND date_trunc('month', current_date) < dgp.end_date --Edellisen kuun viimeisen päivän mukaan
    ) as ryhmän_lapsimäärä_ed_kuun_lopussa
FROM daycare_group dg
         JOIN daycare d on dg.daycare_id = d.id
WHERE (current_date - interval '3 months' <= d.closing_date
OR d.closing_date is null)
AND (current_date - interval '3 months' <= dg.end_date
    OR dg.end_date is null);