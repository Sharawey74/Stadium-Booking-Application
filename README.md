# 🏟️ Stadium Booking Application

A comprehensive Java-based stadium booking management system that provides both console and GUI interfaces for managing stadium facilities, making bookings, and tracking reservations. The application supports multiple facility types including seating sections and conference rooms with real-time availability tracking.

![Java](https://img.shields.io/badge/Java-17+-orange.svg?style=flat&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17+-blue.svg?style=flat&logo=openjfx)
![License](https://img.shields.io/badge/License-MIT-green.svg)

## 🌟 Overview

The Stadium Booking Application is designed to streamline the management of stadium facilities and bookings. It offers a dual-interface approach with both command-line and modern JavaFX GUI options, making it accessible for different user preferences and deployment scenarios.

## ✨ Key Features

### 🏢 Facility Management
- **Multiple Facility Types**: Support for Seating Sections and Conference Rooms
- **Dynamic Capacity Management**: Real-time tracking of available units
- **Facility Details**: Comprehensive information including seat types and amenities
- **Custom Configurations**: VIP/Regular seating options and projector availability

### 📅 Booking System
- **Real-time Availability**: Instant capacity checking and validation
- **Date/Time Validation**: Prevents past-date bookings and ensures proper formatting
- **Conflict Prevention**: Duplicate booking detection and prevention
- **Flexible Booking Units**: Support for partial capacity bookings

### 🖥️ Dual Interface Support
- **Console Interface**: Traditional command-line interface for system integration
- **JavaFX GUI**: Modern graphical interface with animations and visual feedback
- **Cross-platform Compatibility**: Runs on Windows, macOS, and Linux

### 📊 Advanced Features
- **Animated Backgrounds**: Dynamic visual effects in GUI mode
- **Table Views**: Comprehensive data display with sortable columns
- **Input Validation**: Robust error handling and user feedback
- **Status Tracking**: Real-time system status updates

## 🏗️ System Architecture

### Core Components

```
src/
├── StadiumBookingSystem.java      # Console-based main application
├── StadiumBookingSystemFX.java    # JavaFX GUI application
├── StadiumManager.java            # Core business logic and facility management
├── StadiumFacility.java           # Base facility class with common functionality
├── SeatingSection.java            # Seating section implementation
├── ConferenceRoom.java            # Conference room implementation
└── Booking.java                   # Booking management and validation
```

### Class Hierarchy

```
StadiumFacility (Abstract Base)
├── SeatingSection
│   ├── Seat Type (VIP/Regular)
│   └── Capacity Management
└── ConferenceRoom
    ├── Projector Availability
    └── Meeting Room Features
```

## 🚀 Getting Started

### Prerequisites

- **Java 17 or higher** (with JavaFX modules)
- **JavaFX SDK** (if not included in your JDK)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Sharawey74/Stadium-Booking-Application.git
   cd Stadium-Booking-Application
   ```

2. **Compile the Application**
   ```bash
   # For console version
   javac -d out src/*.java
   
   # For JavaFX version (adjust module path as needed)
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d out src/*.java
   ```

3. **Run the Application**
   ```bash
   # Console version
   java -cp out StadiumBookingSystem
   
   # JavaFX GUI version
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out StadiumBookingSystemFX
   ```

### Quick Start

1. **Launch the Application**: Choose between console or GUI interface
2. **Add Facilities**: Create seating sections or conference rooms
3. **Make Bookings**: Reserve facilities with date/time validation
4. **View Status**: Monitor current bookings and availability
5. **Manage Reservations**: Cancel or modify existing bookings

## 🎯 Usage Guide

### Console Interface

The console interface provides a text-based menu system:

```
Stadium Booking System Menu:
1. Add Facility
2. View Facilities  
3. Make Booking
4. View Bookings
5. Cancel Booking
6. Exit
```

#### Adding Facilities

**Seating Section:**
- Facility Name: "Main Stadium Seating"
- Capacity: 1000
- Seat Type: VIP or Regular

**Conference Room:**
- Facility Name: "Executive Conference Room"
- Capacity: 50
- Projector Available: true/false

#### Making Bookings

```
Enter facility name: Main Stadium Seating
Enter booking date (yyyy-MM-dd): 2024-12-25
Enter booking time (HH:mm): 14:30
Enter number of units to book: 100
```

### JavaFX GUI Interface

The GUI provides an intuitive visual interface with:

- **Animated Background**: Dynamic visual effects
- **Navigation Panel**: Easy access to all functions
- **Data Tables**: Comprehensive facility and booking views
- **Dialog Forms**: User-friendly input forms
- **Real-time Feedback**: Instant validation and status updates

#### Key GUI Features

1. **Facility Management Table**
   - Facility Name and Type
   - Current Capacity and Bookings
   - Availability Status
   - Additional Information

2. **Booking Management**
   - Interactive date/time pickers
   - Dropdown facility selection
   - Real-time availability checking
   - Conflict detection

3. **Visual Feedback**
   - Success/error alerts
   - Progress indicators
   - Status bar updates

## 🔧 Technical Specifications

### Core Classes

#### StadiumFacility (Base Class)
```java
public class StadiumFacility {
    private final String facilityName;
    private final int capacity;
    private int currentBookings;
    
    public boolean checkAvailability(int requestedUnits);
    public void bookUnits(int units);
}
```

#### Booking Management
```java
public class Booking {
    private final StringProperty facilityName;
    private final ObjectProperty<LocalDate> bookingDate;
    private final StringProperty bookingTime;
    
    public static void makeBooking(String facilityName, String date, String time);
    public static boolean cancelBooking(String facilityName, String date, String time);
}
```

### Data Validation

#### Date Validation
- **Format**: yyyy-MM-dd (ISO 8601)
- **Past Date Prevention**: No bookings for dates before today
- **Range Checking**: Logical date boundaries

#### Time Validation
- **Format**: HH:mm (24-hour format)
- **Range**: 00:00 to 23:59
- **Granularity**: Minute-level precision

#### Capacity Validation
- **Positive Values**: Only positive booking units allowed
- **Availability Check**: Real-time capacity verification
- **Overflow Prevention**: Booking cannot exceed facility capacity

## 🎨 GUI Design Features

### Visual Elements
- **Gradient Backgrounds**: Professional blue-to-dark gradients
- **Animated Effects**: Smooth transitions and dynamic backgrounds
- **Modern Typography**: Clean, readable fonts with proper hierarchy
- **Responsive Layout**: Adapts to different screen sizes

### Color Scheme
```css
Primary Blue: rgb(5, 119, 250)
Dark Blue: rgb(15, 32, 60)
White Accents: rgb(255, 255, 255)
Success Green: Standard JavaFX
Error Red: Standard JavaFX
```

### Animation Features
- **Background Particles**: Animated floating elements
- **Smooth Transitions**: Fade-in/fade-out effects
- **Interactive Feedback**: Button hover and click animations

## 📊 Data Management

### In-Memory Storage
- **Facilities List**: ArrayList of StadiumFacility objects
- **Bookings List**: ArrayList of Booking objects
- **Real-time Updates**: Immediate reflection of changes

### Data Persistence
- **Session-based**: Data persists during application runtime
- **Future Enhancement**: Database integration planned
- **Export Capability**: Booking data can be extracted

## 🔒 Error Handling

### Input Validation
- **Date Format Checking**: Regex-based validation
- **Time Format Verification**: 24-hour format enforcement
- **Capacity Bounds**: Positive integer validation
- **Facility Existence**: Real-time facility lookup

### User Feedback
- **Console Messages**: Clear error descriptions
- **GUI Alerts**: Modal dialogs with specific error information
- **Status Updates**: Real-time status bar notifications

## 🚀 Performance Features

- **Efficient Searching**: O(n) facility and booking lookups
- **Memory Management**: Careful object lifecycle management
- **Event Handling**: Responsive UI interactions
- **Animation Optimization**: Smooth 60fps animations

## 🔮 Future Enhancements

### Planned Features
- **Database Integration**: MySQL/PostgreSQL support
- **User Authentication**: Role-based access control
- **Payment Processing**: Booking payment integration
- **Reporting System**: Analytics and usage reports
- **Mobile App**: Android/iOS companion apps

### Technical Improvements
- **REST API**: Web service endpoints
- **Configuration Files**: External settings management
- **Logging System**: Comprehensive audit trails
- **Unit Testing**: Automated test coverage

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Add comprehensive JavaDoc comments
- Include unit tests for new features
- Maintain consistent code formatting
- Update documentation for API changes

## 📞 Support

For questions, issues, or contributions:
- **Repository**: [Stadium-Booking-Application](https://github.com/Sharawey74/Stadium-Booking-Application)
- **Issues**: Use GitHub Issues for bug reports and feature requests
- **Discussions**: Use GitHub Discussions for general questions

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- JavaFX community for excellent GUI framework
- Java development best practices
- Stadium management industry standards
- Open source community contributions

---

**© 2024 Stadium Booking Application** - Efficient Facility Management Solution


---

**Note**: The search results were limited to the first 10 files. For a complete view of all files in the repository, please visit: https://github.com/Sharawey74/Stadium-Booking-Application/tree/main/src
