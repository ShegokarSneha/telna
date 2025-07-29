# Telna Automation Framework

This project is a **Playwright TestNG automation framework** built using **Java** and **Maven**. 
It is designed for end-to-end UI automation testing of the OrangeHRMS application.
This Project also having Api Automation using **RestAssured** built using **Java** and **Maven**.

---

## 📁 Project Structure

entrata/
├── src/
│ ├── test/java/
│ │ ├── com.telna.pages # Page Object Model classes
│ │ ├── com.telna.pojoclasses # Api req POJO Class
│ │ ├── com.telna.reporting # Extent report utilities
│ │ ├── com.telna.setup # Base Setup for Test
│ │ ├── com.telna.tests # Test classes
│ │ └── com.telna.utility # Helpers like Config, Assertions
│ └── test/resources/
│ ├── data/booking.json # Test data
│ └── properties/config.properties # Configuration file
├── pom.xml # Maven dependencies and plugins
└── .gitignore


---

## ⚙️ Tools & Technologies

- **Java 8+**
- **Playwright**
- **TestNG**
- **Maven**
- **ExtentReports** for reporting
- **RestAssured** Api Testing
- **Json** for test data
- **Page Object Model (POM)** design pattern

---

## 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone <repo-url>
   cd telna

2. **Install dependencies**
   ```bash
   mvn clean install

3. **Run tests**
   ```bash
   mvn test

---
**Test Structure**

1. ApplicationTests.java contains UI test methods.
2. RestApiTests.java contains test regarding Api Automation
3. Reports are generated via ExtentReports

---
**Reports**

1. After test execution, reports are saved in the configured location
2. Screenshots on failure are also available.

---
**Configuration**

Application and test-specific properties can be configured in:

1. config.properties for environment URLs, credentials, etc.
2. booking.json for Api RequestBody