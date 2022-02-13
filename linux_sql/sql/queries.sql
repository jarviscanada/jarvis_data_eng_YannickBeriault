SELECT cpu_number, id, total_mem FROM host_info ORDER BY cpu_number, total_mem DESC;

CREATE VIEW groupedDatesPlusMemUsed AS
    SELECT hU.host_id, hI.hostname, date_trunc('hour', hU.timestamp) + date_part('minute', hU.timestamp):: \
            int / 5 * interval '5 min' AS groupedDate, (((total_mem - memory_free) * 100) / total_mem) \
            AS avg_used_mem_percentage
    FROM host_usage AS hU JOIN host_info AS hI
    ON hU.host_id = hI.id;

SELECT host_id, hostname, groupedDate, ROUND(AVG(avg_used_mem_percentage)) AS avg_used_mem_percentage
FROM groupedDatesPlusMemUsed
GROUP BY host_id, hostname, groupedDate
ORDER BY host_id, groupeddate;

SELECT counted.groupeddate
FROM (
    SELECT groupedDate, COUNT(*) AS countFiveMinutes
    FROM groupedDatesPlusMemUsed
    GROUP BY groupedDate) AS counted
WHERE counted.countFiveMinutes < 3;