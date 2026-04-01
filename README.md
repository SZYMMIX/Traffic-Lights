# Smart Traffic Lights Simulation

## Project Description
**Smart Traffic Lights** is an advanced intersection simulation designed to optimize traffic flow while ensuring maximum safety. The system dynamically adjusts traffic light cycles based on real-time traffic density on individual lanes.

The simulation models a four-way intersection (North, South, East, West), where each road consists of three dedicated lanes: **Left Turn, Forward and Right Turn**.

## 🧠 Smart Logic & Safety
### Greedy Optimization Algorithm
The heart of the simulation is a **Greedy Optimization Algorithm** that manages 12 independent lanes. In each step, the system:
1.  **Analyzes Queues:** Scans all lanes and identifies those with waiting vehicles.
2.  **Prioritizes:** Sorts lanes by queue length (the most crowded lanes get the highest priority).
3.  **Conflict Check:** Iteratively selects lanes to receive a green light, ensuring that no selected lane conflicts with those already chosen for the current phase.
4.  **Phase Persistence:** Implements `MIN_GREEN_TIME` to ensure traffic stability.

### Collision Management & Realism
*   **Safety First:** A dedicated `CollisionManager` uses a rigorous mapping of intersecting paths. For example, a North-Forward path and an East-Forward path can never have a green light simultaneously.
*   **Realistic Cycles:** Unlike simple toggles, the system implements realistic transitions: `RED` -> `RED_YELLOW` -> `GREEN` -> `YELLOW` -> `RED`.
*   **Symmetric Difference:** The system uses set-theory (symmetric difference) to transition only the necessary lanes, allowing non-conflicting green lights to remain active across different phases.

## 🐾 Features
| Feature | Description                                                                         |
| :--- |:------------------------------------------------------------------------------------|
| **🚦 Smart Logic** | Dynamic light adjustment based on real-time vehicle counts.                         |
| **🛡️ Collision Safety** | Guaranteed prevention of conflicting green lights using a pre-defined conflict map. |
| **🏎️ Multi-lane Support** | 12 independent lanes (3 per road) with dedicated signaling.                         |
| **💻 CLI Mode** | Process simulation commands via JSON files as per technical requirements.           |
| **🌐 Live GUI** | A real-time React dashboard to observe and interact with the simulation.            |
| **🧪 Testing** | Comprehensive JUnit 5 coverage for core logic and safety rules                      |

## 🚀 How to Run
### Requirements
*   **Java 25**
*   **Node.js & npm** (For the frontend)
*   **Gradle**

### Steps
1. **Clone and Build:**
   ```bash
   git clone https://github.com/SZYMMIX/traffic-lights
   cd traffic-lights/backend
   ./gradlew build
   ```

2. **Run CLI Mode:**
   Process an `input.json` file and produce an `output.json`:
   ```bash
   ./gradlew :cli:run --args="input.json output.json"
   ```

3. **Run GUI Mode (Live Simulator):**
    *   **Start Backend Server:**
        ```bash
        ./gradlew :server:bootRun
        ```
    *   **Start Frontend:**
        ```bash
        cd ../frontend
        npm install
        npm run dev
        ```
    *   Open [http://localhost:5173](http://localhost:5173) in your browser.

## ⚙️ Configuration (JSON Commands)
The CLI accepts a JSON file with a list of commands:
*   `addVehicle`: Adds a car with a specific `vehicleId`, `startRoad`, and `endRoad`.
*   `step`: Executes one simulation tick (updates lights and moves vehicles).

The output provides a `stepStatuses` list containing `leftVehicles` for each step.

## 🛠️ Project Structure
The project is organized into a multi-module Gradle setup to separate concerns:

```text
.
├── backend
│   ├── logic/          # Core domain: SimulationEngine, CollisionManager, Lanes
│   │   └── src/test/   # Extensive Unit Tests (Greedy logic, Safety rules)
│   ├── cli/            # JSON processing module
│   └── server/         # Spring Boot REST API (Serving snapshots to UI)
├── frontend
│   ├── src/
│   │   ├── components/ # React components
│   │   ├── styles/     # Dedicated CSS-in-JS/External styles
│   │   └── App.tsx     # Live simulation dashboard logic
└── README.md
```

---
**Author:** Szymon Cimochowski
