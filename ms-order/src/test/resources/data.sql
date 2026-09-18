
INSERT INTO restaurants (id, name, address, zipcode, locate, summary, description, picture)
VALUES (1, 'Le Gourmet Lyon', '25 Avenue des Fleurs', '6902', 'Lausanne', 'Cuisine raffinée', 'Restaurant gastronomique spécialisé dans la cuisine traditionnelle lyonnaise revisitée.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDekeubiw_nphOIeVlfVqIorxTdaqH7nATB4Ut0tdDCg&s=10');

INSERT INTO restaurants (id, name, address, zipcode, locate, summary, description, picture)
VALUES (2, 'La Trattoria Bellecour', '14 Rue de la République', '6902', 'Lausanne', 'Spécialités italiennes', 'Authentique trattoria italienne proposant des pizzas au feu de bois et pâtes fraîches maison.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDekeubiw_nphOIeVlfVqIorxTdaqH7nATB4Ut0tdDCg&s=10');

INSERT INTO dishs (id, name, price, description, id_restaurant) VALUES
(1, 'Quenelle de Brochet', 18.50, 'Quenelle artisanale nappée de sa sauce Nantua d exception.', 1),
(2, 'Salade Lyonnaise', 12.00, 'Salade verte, lardons grillés, croutons et œuf poché.', 1),
(3, 'Filet de Bœuf Pêcher', 24.90, 'Filet de bœuf tendre servi avec gratin dauphinois.', 1),
(4, 'Pizza Margherita', 11.50, 'Sauce tomate, mozzarella di bufala, basilic frais.', 2),
(5, 'Tiramisu Classico', 6.50, 'Dessert italien traditionnel au café et mascarpone.', 2),
(6, 'Pâtes Carbonara', 14.00, 'Pâtes fraîches avec guanciale, jaune d œuf et pecorino romano.', 2);
