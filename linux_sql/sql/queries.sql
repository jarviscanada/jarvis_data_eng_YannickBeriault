SELECT cpu_number, id, total_mem
FROM host_info
ORDER BY cpu_number, total_mem DESC;

--The following queries depend on two views that were moved to ./ddl.sql
SELECT groupedDates.host_id, hostname, groupedDate, ROUND(AVG(avg_used_mem_percentage)) AS avg_used_mem_percentage
FROM groupedDates NATURAL JOIN memUsed
GROUP BY groupedDates.host_id, hostname, groupedDate
ORDER BY host_id, groupeddate;

SELECT counted.groupedDate AS server_failures
FROM (
    SELECT groupedDate, COUNT(*) AS countFiveMinutes
    FROM groupedDates
    GROUP BY groupedDate) AS counted
WHERE counted.countFiveMinutes < 3
ORDER BY counted.groupedDate::timestamp;