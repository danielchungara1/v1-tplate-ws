UPDATE USERS SET
                 EMAIL = 'danielchungara1@gmail.com'
WHERE ID = (SELECT ID FROM USERS WHERE USERNAME LIKE 'danielchungara1@gmail.com');