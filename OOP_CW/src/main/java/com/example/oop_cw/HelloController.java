package com.example.oop_cw;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.bson.Document;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HelloController {

    @FXML
    public AnchorPane paneAdmin;
    @FXML
    public AnchorPane PaneAdminNavigator;
    @FXML
    public Button AdminHomeBtn;
    @FXML
    public Button AdminAddNewsBtn;
    @FXML
    public Button AdminViewNewsBtn;
    @FXML
    public Button AdminExitBtn;
    @FXML
    public AnchorPane paneAdminPostNews;
    @FXML
    public TextField AdminTitleInput;
    @FXML
    public TextField AdminURLInput;
    @FXML
    public TextArea AdminDesInput;
    @FXML
    public TextField AdminAuthorInput;
    @FXML
    public TextField AdminDateInput;
    @FXML
    public Button AdminPostNewsBtn;
    @FXML
    public AnchorPane paneAdminView_delNews;
    @FXML
    public TableView<Admin_articles> AdminViewPostNewsTabla;
    @FXML
    public TableColumn<Admin_articles, String> AdminViewTitle;
    @FXML
    public TableColumn<Admin_articles, String> AdminViewDes;
    @FXML
    public TableColumn<Admin_articles, String> AdminViewURL;
    @FXML
    public TableColumn<Admin_articles, String> AdminViewAuthor;
    @FXML
    public TableColumn<Admin_articles, String> AdminViewDate;
    @FXML
    public Button AdminDelNewsBtn;
    @FXML
    public AnchorPane paneUser;
    @FXML
    public AnchorPane PaneUserNavigator;
    @FXML
    public Button UserHomeBtn;
    @FXML
    public Button UserViewNewsBtn;
    @FXML
    public Button UserNewsRecomBtn;
    @FXML
    public Button UserExitBtn;
    @FXML
    public AnchorPane paneView_RateNews;
    @FXML
    public Pane View_News_Pane;
    @FXML
    public Pane Rate_News_Pane;
    @FXML
    public Label RateNewsTitle;
    @FXML
    public Slider RatingSlider;
    @FXML
    public TextArea EnterFeedbackToNews;
    @FXML
    public Button SubmitRatingsBtn;
    @FXML
    public Label DisplayRatings;
    @FXML
    public AnchorPane paneGetRecommendations;
    @FXML
    public AnchorPane paneLogIn;
    @FXML
    public AnchorPane PaneLogInNavigator;
    @FXML
    public Button homeBtn;
    @FXML
    public Button logBtn2;
    @FXML
    public Button logBtn3;
    @FXML
    public Button logBtn1;
    @FXML
    public Button exitBtn;
    @FXML
    public AnchorPane paneReister;
    @FXML
    public TextField registerInputFName;
    @FXML
    public Button btnSignUp;
    @FXML
    public TextField registerInputLName;
    @FXML
    public TextField registerInputEmail;
    @FXML
    public TextField registerInputPhone;
    @FXML
    public TextField registerInputUserName;
    @FXML
    public PasswordField registerInputPassword;
    @FXML
    public PasswordField registerInputConfPassword;
    @FXML
    public AnchorPane paneUserLogin;
    @FXML
    public TextField UserLogin_UserNameInput;
    @FXML
    public PasswordField UserLogin_PasswordInput;
    @FXML
    public Button UserLoginBtn;
    @FXML
    public AnchorPane paneAdminLogIn;
    @FXML
    public TextField AdminLogIn_UserNameInput;
    @FXML
    public Button AdminLogInBtn;
    @FXML
    public AnchorPane paneHome;
    @FXML
    public AnchorPane PaneHomeNavigator;
    @FXML
    public Button HomePgHomeBtn;
    @FXML
    public Button HomePgLogBtn2;
    @FXML
    public Button HomePgLogBtn3;
    @FXML
    public Button HomePgLogBtn1;
    @FXML
    public Button HomePgExitBtn;
    @FXML
    public Label registerLabel;
    @FXML
    public Label userLoginLabel;
    @FXML
    public Label AdminLoginLabel;
    @FXML
    public AnchorPane userHomePage;
    @FXML
    public ListView<String> NewsListView;
    @FXML
    public Button GetNewsBtn;
    @FXML
    public ListView <String>  GetRecommendatioinsList;
    @FXML
    public Button GetRecommendatioinsbtn;
    @FXML
    public AnchorPane AdminHomePage;
    @FXML
    public Button AdminDash;
    @FXML
    public TextArea AdminConInput;
    @FXML
    public Button UserDash;

    private final String ADMIN_CODE = "Admin1234";
    public static String currentUsername;

    // OOP Concept: Abstraction
    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Initially display Home pane with LogIn Navigator
        showMainPane(paneHome, PaneLogInNavigator);

        // OOP Concept: Polymorphism
        // Use of `PropertyValueFactory` to dynamically bind table columns to class properties.
        AdminViewTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        AdminViewDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        AdminViewURL.setCellValueFactory(new PropertyValueFactory<>("url"));
        AdminViewAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        AdminViewDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Load articles into the TableView
        reloadAdminArticles();

    }
    // OOP Concept: Encapsulation and Abstraction
    // Handles fetching articles from the database and binding them to the UI.
    public void reloadAdminArticles() {
        try {
            // Fetch all admin-posted articles
            List<Admin_articles> loadedArticles = database.fetchAdminArticles();

            if (loadedArticles == null || loadedArticles.isEmpty()) {
                System.out.println("No articles found in the database.");
                return;
            }

            // Convert the list to an ObservableList
            ObservableList<Admin_articles> articleList = FXCollections.observableArrayList(loadedArticles);

            // Bind the ObservableList to the TableView
            AdminViewPostNewsTabla.setItems(articleList);

            System.out.println("Articles reloaded into TableView: " + articleList.size());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Failed to reload articles", e.getMessage());
        }
    }



    @FXML
    //Capitalize First Letter In Each Word
    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder(str.length());
        String[] words = str.split("\\s");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1).toLowerCase());
            }
            if (!(result.length() == str.length()))
                result.append(' ');
        }
        return result.toString();

    }

    // Main button click handler
    @FXML
    // Main Navigation Controller
    private void buttonClicksMain(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Home Page Navigation
        if (clickedButton == HomePgLogBtn1 ||
                clickedButton == HomePgLogBtn2 ||
                clickedButton == HomePgLogBtn3) {
            showMainPane(paneLogIn, PaneLogInNavigator);

            // Determine which login pane to show based on the specific button
            if (clickedButton == HomePgLogBtn1) {
                showChildPane(paneReister, paneLogIn);
            } else if (clickedButton == HomePgLogBtn2) {
                showChildPane(paneUserLogin, paneLogIn);
            } else if (clickedButton == HomePgLogBtn3) {
                showChildPane(paneAdminLogIn, paneLogIn);
            }
        }

        // Login Navigator Buttons
        if (clickedButton == logBtn1) {
            showChildPane(paneReister, paneLogIn);
        } else if (clickedButton == logBtn2) {
            showChildPane(paneUserLogin, paneLogIn);
        } else if (clickedButton == logBtn3) {
            showChildPane(paneAdminLogIn, paneLogIn);
        }

        // User Login Navigation
        if (clickedButton == UserLoginBtn) {
            buttonClicksUser(event);
        }

        // Admin Login Navigation
        if (clickedButton == AdminLogInBtn) {
            buttonClicksAdmin(event);
        }
    }

    // User Pane Navigation Controller
    @FXML
    private void buttonClicksUser(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Hide all User Child Panes initially
        hidePanes(new Pane[]{userHomePage, paneView_RateNews, paneGetRecommendations});

        // Handle User Navigator Buttons
        if (clickedButton == UserHomeBtn) {
            showChildPane(userHomePage, paneUser); // Show Home Page
        } else if (clickedButton == UserViewNewsBtn) {
            showChildPane(paneView_RateNews, paneUser); // Show View News Page
        } else if (clickedButton == UserNewsRecomBtn) {
            showChildPane(paneGetRecommendations, paneUser); // Show Recommendations Page
        }
    }

    // Admin Pane Navigation Controller
    @FXML
    private void buttonClicksAdmin(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Hide all User Child Panes initially
        hidePanes(new Pane[]{AdminHomePage, paneAdminPostNews, paneAdminView_delNews});

        // Handle User Navigator Buttons
        if (clickedButton == AdminHomeBtn) {
            showChildPane(AdminHomePage, paneAdmin); // Show Home Page
        } else if (clickedButton == AdminAddNewsBtn) {
            showChildPane(paneAdminPostNews, paneAdmin); // Show View News Page
        } else if (clickedButton == AdminViewNewsBtn) {
            showChildPane(paneAdminView_delNews, paneAdmin); // Show Recommendations Page
        }
    }

    // Show main pane and its associated navigator
    private void showMainPane(Pane mainPane, Pane navigator) {
        // Hide all main panes and navigators
        Pane[] mainPanes = {paneHome, paneLogIn, paneUser, paneAdmin};
        Pane[] navigators = {PaneLogInNavigator, PaneUserNavigator, PaneAdminNavigator};

        for (Pane pane : mainPanes) pane.setVisible(false);
        for (Pane nav : navigators) nav.setVisible(false);

        // Show the specified main pane and navigator
        mainPane.setVisible(true);
        navigator.setVisible(true);
    }

    // Show child panes within a main pane
    private void showChildPane(Pane childPane, Pane parentPane) {
        // Hide child panes based on parent
        Pane[] logInChildPanes = {paneReister, paneUserLogin, paneAdminLogIn};
        Pane[] userChildPanes = {paneView_RateNews, paneGetRecommendations};
        Pane[] adminChildPanes = {paneAdminPostNews, paneAdminView_delNews};

        if (parentPane == paneLogIn) hidePanes(logInChildPanes);
        if (parentPane == paneUser) hidePanes(userChildPanes);
        if (parentPane == paneAdmin) hidePanes(adminChildPanes);

        // Show the specific child pane
        childPane.setVisible(true);
    }

    // Utility method to hide multiple panes
    private void hidePanes(Pane[] panes) {
        for (Pane pane : panes) pane.setVisible(false);
    }

    public void Register(ActionEvent event) {
        User_Register user = null;

        try {
            String firstName = capitalize(registerInputFName.getText());
            String lastName = capitalize(registerInputLName.getText());
            String email = registerInputEmail.getText();
            String phoneNumber = registerInputPhone.getText();
            String username = registerInputUserName.getText();
            String password = registerInputPassword.getText();
            String confirmPassword = registerInputConfPassword.getText();

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                registerLabel.setText("Error, please fill all the fields");
                return;
            }
            // Match Password
            if (!password.equals(confirmPassword)) {
                registerLabel.setText("Error, passwords do not match");
                return;
            }
            // Check Numbers In Names
            if(!firstName.matches("^\\D+$") || !lastName.matches("^\\D+$")) {
                registerLabel.setText("There Are Numbers In The Name. Can't Contain Numbers");
                return;
            }
            // Check Email Format
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                registerLabel.setText("Invalid email format.");
                return;
            }

            user = new User_Register(firstName, lastName, email, phoneNumber, username, password, confirmPassword);

        } catch (Exception e) {
            registerLabel.setText("Unexpected error. Please try again.");
        }
        try {
            // Check if the username already exists
            Optional<Document> existingUser = database.findUserByUsername(user.getusername());
            if (existingUser.isPresent()) {
                registerLabel.setText("Error, username already exists.");
                return;
            }

            // Save user to the database
            database.saveUser(user);

            // Clear fields after adding data
            clearInputFields();
            registerLabel.setText("User registered successfully!");

            // Show success message briefly
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> registerLabel.setText("")));
            timeline.play();

        } catch (Exception e) {
            registerLabel.setText("Database error. Please try again.");
        }
    }

    private void clearInputFields() {
        registerInputFName.setText("");
        registerInputLName.setText("");
        registerInputEmail.setText("");
        registerInputPhone.setText("");
        registerInputUserName.setText("");
        registerInputPassword.setText("");
        registerInputConfPassword.setText("");
        registerLabel.setText("");
    }

    public void UserLogin(ActionEvent event) {
        String username = UserLogin_UserNameInput.getText();
        String password = UserLogin_PasswordInput.getText();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            userLoginLabel.setText("Error, please fill all the fields");
            return;
        }

        try {
            // Query the database for the user
            Optional<Document> userDoc = database.findUserByUsername(username);

            if (userDoc.isPresent() && userDoc.get().getString("password").equals(password)) {
                // Login successful - display paneUser
                // Show paneUser, PaneUserNavigator, and UserHomePage explicitly
                showMainPane(paneUser, PaneUserNavigator);
                showChildPane(userHomePage, paneUser); // Initially show UserHomePage

                userLoginLabel.setText("Success, Login successful!");
            } else {
                userLoginLabel.setText("Error, Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            userLoginLabel.setText("Error, Database error. Please try again.");
        }
        UserLogin_UserNameInput.setText("");
        UserLogin_PasswordInput.setText("");
        userLoginLabel.setText("");

    }

    public void AdminLogIn(ActionEvent event) {
        String adminCode = AdminLogIn_UserNameInput.getText();

        // Validate input field
        if (adminCode.isEmpty()) {
            AdminLoginLabel.setText("Error, Please enter the admin code.");
            return;
        }

        // Check admin code
        if (ADMIN_CODE.equals(adminCode)) {
            // Login successful - display paneAdmin
            showMainPane(paneAdmin, PaneAdminNavigator);
            showChildPane(AdminHomePage, paneAdmin);

            AdminLoginLabel.setText("Success, Admin login successful!");
        } else {
            AdminLoginLabel.setText("Error, Invalid admin code.");
        }
    }

    public void GetAllNews(ActionEvent event) {
        // List of API URLs
        List<String> apiUrls = List.of(
                "https://newsapi.org/v2/top-headlines?country=us&apiKey=ac505d45813d48b4a1793a7e113a3063",
                "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=ac505d45813d48b4a1793a7e113a3063",
                "https://newsapi.org/v2/everything?domains=wsj.com&apiKey=ac505d45813d48b4a1793a7e113a3063"
        );

        HttpClient client = HttpClient.newHttpClient();
        ObservableList<String> allNewsTitles = FXCollections.observableArrayList();
        Map<String, JsonObject> articleDetails = new HashMap<>();

        // Fetch articles from all APIs
        for (String apiUrl : apiUrls) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                        JsonArray articles = jsonResponse.getAsJsonArray("articles");

                        // Extract details for each article
                        for (int i = 0; i < articles.size(); i++) {
                            JsonObject article = articles.get(i).getAsJsonObject();

                            // Safely retrieve fields with null checks
                            String title = getJsonFieldSafely(article, "title");
                            String description = getJsonFieldSafely(article, "description");
                            String fullText = title + " " + description;

                            if (title != null && !title.isEmpty()) {
                                allNewsTitles.add(fullText); // Add article text for categorization
                                articleDetails.put(title, article); // Map title to full article
                            }
                        }

                        // Categorize articles after fetching from the API
                        Map<String, List<String>> categorizedArticles = categorizeArticles(allNewsTitles);

                        // Update the UI with categorized articles
                        Platform.runLater(() -> {
                            displayCategorizedArticles(categorizedArticles, articleDetails);

                            // Set up the click handler for the articles
                            setupNewsClickHandler(articleDetails);
                        });
                    })
                    .exceptionally(ex -> {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Failed to fetch news from API: " + apiUrl);
                            alert.setContentText(ex.getMessage());
                            alert.showAndWait();
                        });
                        return null;
                    });
        }
    }

    private Map<String, List<String>> categorizeArticles(ObservableList<String> articles) {
        // Predefined categories and keywords
        Map<String, List<String>> categories = Map.of(
                "Technology", List.of("tech", "software", "hardware", "innovation", "gadget"),
                "Health", List.of("medicine", "COVID", "vaccine", "health", "disease","cancer","hospitals","surgery"),
                "Sports", List.of("snooker","champion","game", "match", "tournament", "player", "sports", "Sports News","Sports Injuries"),
                "AI", List.of("AI", "artificial intelligence", "machine learning", "neural", "Robotics", "Cybersecurity"),
                "Business", List.of("economy", "market", "finance", "business", "Stock","investment","marketing","sales"),
                "Politics", List.of("Trump", "Biden", "democracy", "government", "prime minister","president","bombing")
        );

        Map<String, List<String>> categorizedArticles = new HashMap<>();

        for (String article : articles) {
            boolean categorized = false;

            for (Map.Entry<String, List<String>> entry : categories.entrySet()) {
                String category = entry.getKey();
                List<String> keywords = entry.getValue();

                for (String keyword : keywords) {
                    if (article.toLowerCase().contains(keyword.toLowerCase())) {
                        categorizedArticles.computeIfAbsent(category, k -> new ArrayList<>()).add(article);
                        categorized = true;
                        break;
                    }
                }
                if (categorized) break;
            }

            // If no category matched, assign to "Uncategorized"
            if (!categorized) {
                categorizedArticles.computeIfAbsent("Uncategorized", k -> new ArrayList<>()).add(article);
            }
        }

        return categorizedArticles;
    }

    private void displayCategorizedArticles(Map<String, List<String>> categorizedArticles, Map<String, JsonObject> articleDetails) {
        ObservableList<String> displayList = FXCollections.observableArrayList();

        // Add categories and their articles to the list
        categorizedArticles.forEach((category, articles) -> {
            displayList.add("Category: " + category); // Add category header
            articles.forEach(article -> displayList.add(" - " + article)); // Add articles under the category
        });

        // Update the ListView with categorized articles
        NewsListView.setItems(displayList);

        GetRecommendatioinsList.setItems(displayList);

        // Set click handler for selecting articles
        NewsListView.setOnMouseClicked(event -> {
            String selectedItem = NewsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && !selectedItem.startsWith("Category:")) {
                RateNewsTitle.setText(selectedItem); // Display article title in the label
            }
        });
    }

    private void setupNewsClickHandler(Map<String, JsonObject> articleDetails) {
        NewsListView.setOnMouseClicked((MouseEvent e) -> {
            // Single click to select article for rating
            if (e.getClickCount() == 1) {
                String selectedTitle = NewsListView.getSelectionModel().getSelectedItem();

                // Remove the category prefix if present
                if (selectedTitle != null && selectedTitle.startsWith(" - ")) {
                    selectedTitle = selectedTitle.substring(3);
                }

                // Find the matching article details
                JsonObject matchingArticle = null;
                for (Map.Entry<String, JsonObject> entry : articleDetails.entrySet()) {
                    String fullText = entry.getKey() + " " + getJsonFieldSafely(entry.getValue(), "description", "");
                    if (fullText.equals(selectedTitle)) {
                        matchingArticle = entry.getValue();
                        break;
                    }
                }

                // Update RateNewsTitle with the selected article
                if (selectedTitle != null && !selectedTitle.startsWith("Category:")) {
                    RateNewsTitle.setText(selectedTitle);

                    // Optional: Reset rating and feedback fields
                    RatingSlider.setValue(1);
                    EnterFeedbackToNews.clear();
                    DisplayRatings.setText("");
                }
            }
            // Double-click to open article in browser (keep existing double-click functionality)
            else if (e.getClickCount() == 2) {
                // Existing double-click code to open article in browser
                String selectedTitle = NewsListView.getSelectionModel().getSelectedItem();


                // Remove the category prefix if present
                if (selectedTitle != null && selectedTitle.startsWith(" - ")) {
                    selectedTitle = selectedTitle.substring(3);
                }

                System.out.println("Selected Title: " + selectedTitle); // Debug log

                // Find the matching article details
                JsonObject matchingArticle = null;
                for (Map.Entry<String, JsonObject> entry : articleDetails.entrySet()) {
                    String fullText = entry.getKey() + " " + getJsonFieldSafely(entry.getValue(), "description", "");
                    if (fullText.equals(selectedTitle)) {
                        matchingArticle = entry.getValue();
                        break;
                    }
                }

                if (matchingArticle != null) {
                    String fullArticleUrl = getJsonFieldSafely(matchingArticle, "url");
                    System.out.println("Full Article URL: " + fullArticleUrl); // Debug log

                    if (fullArticleUrl != null && !fullArticleUrl.isEmpty()) {
                        try {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(new URI(fullArticleUrl));
                            } else {
                                System.err.println("Desktop is not supported. Cannot open browser.");
                                showAlert(Alert.AlertType.ERROR, "Browser Error", "Desktop is not supported", "Cannot open browser.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Error", "Unable to Open Article", "Could not open the article. Please try again.");
                        }
                    } else {
                        showAlert(Alert.AlertType.WARNING, "No URL", "No URL Available", "This article does not have a valid URL.");
                    }
                }
            }
        });
    }

    // Helper method to safely get JSON fields
    private String getJsonFieldSafely(JsonObject jsonObject, String fieldName) {
        return getJsonFieldSafely(jsonObject, fieldName, null);
    }

    private String getJsonFieldSafely(JsonObject jsonObject, String fieldName, String defaultValue) {
        if (jsonObject.has(fieldName) && !jsonObject.get(fieldName).isJsonNull()) {
            return jsonObject.get(fieldName).getAsString();
        }
        return defaultValue;
    }

    public void SubmitRatings(ActionEvent event) {
        String username = HelloController.currentUsername;
        String newsTitle = RateNewsTitle.getText(); // Get the displayed title
        double rating = RatingSlider.getValue();   // Get the rating value
        String feedback = EnterFeedbackToNews.getText(); // Get the feedback text

        if (newsTitle == null || newsTitle.isEmpty()) {
            DisplayRatings.setText("Error: Please select a news article.");
            return;
        }

        if (rating < 1 || rating > 5) {
            DisplayRatings.setText("Error: Rating must be between 1 and 5.");
            return;
        }
        // Provide feedback to the user
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Rating Submitted");
        successAlert.setContentText("Your rating and feedback have been saved.");
        successAlert.showAndWait();


        // Save rating and feedback to database
        database.saveRating(newsTitle, rating, feedback, username);

        // Save user interaction in history
        database.saveUserHistory(username, newsTitle, rating);

        RatingSlider.setValue(1);
        EnterFeedbackToNews.clear();
        DisplayRatings.setText("");
    }

    @FXML
    public void GetRecommendations(ActionEvent event) {
        // OOP Concept: Concurrency
        String currentUser = HelloController.currentUsername;
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture<List<Document>> userHistoryFuture = CompletableFuture.supplyAsync(() -> {
            List<Document> history = database.getUserHistory(currentUser);  // Get rated articles
            System.out.println("User History: " + history); // Debug
            return history;
        }, executor);

        // Use previously fetched articles from NewsListView.getItems()
        CompletableFuture<List<String>> allArticlesFuture = CompletableFuture.supplyAsync(() -> {
            List<String> articles = new ArrayList<>(NewsListView.getItems()); // Get previously fetched articles
            System.out.println("Fetched Articles: " + articles); // Debug
            return articles;
        }, executor);

        userHistoryFuture.thenCombine(allArticlesFuture, (userHistory, allArticles) -> {
            // Create a set of rated article titles
            Set<String> ratedTitles = userHistory.stream()
                    .map(doc -> doc.getString("title"))
                    .collect(Collectors.toSet());

            // Filter out the rated articles
            List<String> unratedArticles = allArticles.stream()
                    .filter(article -> !ratedTitles.contains(article))
                    .collect(Collectors.toList());

            // Now match unrated articles with rated articles based on keywords
            Map<String, Double> articleScores = new HashMap<>();
            for (Document ratedArticle : userHistory) {
                String ratedTitle = ratedArticle.getString("title");
                String ratedDescription = ratedArticle.getString("description");

                // Generate keywords from the rated article (you can use title + description or just description)
                Set<String> ratedKeywords = extractKeywords(ratedTitle + " " + ratedDescription);

                for (String unratedArticle : unratedArticles) {
                    // Here, we're matching unrated articles based on keywords
                    Set<String> unratedKeywords = extractKeywords(unratedArticle);

                    // Calculate keyword match score (simple intersection of keywords)
                    double score = calculateKeywordMatchScore(ratedKeywords, unratedKeywords);

                    if (score > 0) {
                        articleScores.put(unratedArticle, articleScores.getOrDefault(unratedArticle, 0.0) + score);
                    }
                }
            }

            // Sort articles based on the highest match score
            return articleScores.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }).thenAccept(recommendations -> {
            Platform.runLater(() -> {
                if (recommendations.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "No Recommendations", "No articles to recommend based on your history.", null);
                } else {
                    System.out.println("Recommendations: " + recommendations); // Debug
                    ObservableList<String> recommendationsList = FXCollections.observableArrayList(recommendations);
                    GetRecommendatioinsList.setItems(recommendationsList);
                }
            });

        }).exceptionally(ex -> {
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", "Could not fetch recommendations", ex.getMessage()));
            return null;
        });

        executor.shutdown();
    }

    // Extracts keywords from a string (simple whitespace tokenization and stopword removal can be added)
    private Set<String> extractKeywords(String text) {
        Set<String> keywords = new HashSet<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // Clean up non-alphanumeric characters
            if (!word.isEmpty()) {
                keywords.add(word);
            }
        }
        return keywords;
    }

    // Calculates a score based on the intersection of keywords between rated and unrated articles
    private double calculateKeywordMatchScore(Set<String> ratedKeywords, Set<String> unratedKeywords) {
        Set<String> intersection = new HashSet<>(ratedKeywords);
        intersection.retainAll(unratedKeywords);
        return intersection.size(); // Simple count of common keywords
    }
    @FXML
    public void PostNews(ActionEvent event) {
        // Retrieve input values from the text fields
        String title = AdminTitleInput.getText();
        String url = AdminURLInput.getText();
        String description = AdminDesInput.getText();
        String date = AdminDateInput.getText();
        String author = AdminAuthorInput.getText();// Author input
        String content = AdminConInput.getText();

        // Validate input fields
        if (title.isEmpty() || url.isEmpty() || description.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields", "All fields are required to post news.");
            return;
        }

        // Create an Article object
        Admin_articles article = new Admin_articles(title, url, description, date, author, content);

        // Save the article to the database
        database.AdminSaveArticle(article);

        // Add to the ListView and TableView for immediate UI update
        NewsListView.getItems().add(title);
        AdminViewPostNewsTabla.getItems().add(article);

        // Clear input fields
        AdminTitleInput.clear();
        AdminURLInput.clear();
        AdminDesInput.clear();
        AdminDateInput.clear();
        AdminAuthorInput.clear();
        AdminConInput.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("News Posted Successfully");
        alert.setContentText("The news article has been posted and saved.");

    }


    @FXML
    public void AdminDelNews(ActionEvent event) {
        // Get the selected article from the TableView
        Admin_articles selectedArticle = (Admin_articles) AdminViewPostNewsTabla.getSelectionModel().getSelectedItem();

        if (selectedArticle == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No Article Selected", "Please select an article to delete.");
            return;
        }

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this article?");
        alert.setContentText("Title: " + selectedArticle.getTitle());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the article from the database
            database.deleteArticle(selectedArticle);

            // Remove from the TableView and ListView
            AdminViewPostNewsTabla.getItems().remove(selectedArticle);
            NewsListView.getItems().remove(selectedArticle.getTitle());

            showAlert(Alert.AlertType.INFORMATION, "Success", "Article Deleted", "The article has been deleted successfully.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Article Not Deleted", "The deletion was cancelled.");
        }
    }

    

    @FXML
    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Do you want to exit?");
        Optional<ButtonType> output = alert.showAndWait();

        if (output.isPresent() && output.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void BackToDashboard(ActionEvent event) {
        showMainPane(paneHome, PaneLogInNavigator);

    }
}