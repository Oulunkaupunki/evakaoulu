SELECT
    d.name AS toimintayksikkö,
    d.id AS toimintayksikkö_id,
    d.opening_date AS toimintayksikön_alkupvm,
    d.closing_date AS toimintayksikön_loppupvm,
    d.dw_cost_center AS dw_kustannuspaikka,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
            AND p.start_date <= current_date
            AND current_date <= p.end_date
    ) AS toimintayksikön_lapsimäärä,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
            AND p.start_date < date_trunc('month', current_date)
            AND date_trunc('month', current_date) < p.end_date --Edellisen kuun viimeisen päivän mukaan
    ) AS toimintayksikön_lapsimäärä_ed_kuun_lopussa,
    dg.name AS ryhmä,
    dg.id AS ryhmä_id,
    dg.start_date AS ryhmän_alkupvm,
    dg.end_date AS ryhmän_loppupvm,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
            AND dgp.start_date <= current_date
            AND current_date <= dgp.end_date
    ) AS ryhmän_lapsimäärä,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
            AND dgp.start_date < date_trunc('month', current_date)
            AND date_trunc('month', current_date) < dgp.end_date --Edellisen kuun viimeisen päivän mukaan
    ) AS ryhmän_lapsimäärä_ed_kuun_lopussa
FROM daycare_group dg
    JOIN daycare d on dg.daycare_id = d.id
WHERE (current_date - interval '3 months' <= d.closing_date OR d.closing_date is null)
    AND (current_date - interval '3 months' <= dg.end_date OR dg.end_date is null);
