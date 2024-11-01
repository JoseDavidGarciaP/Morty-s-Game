package com.rickandmortygame.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import com.rickandmortygame.personajes.*;

public class GameUI extends Application {
    private Stage primaryStage;
    private Rick jugador;
    private Scene escenaInicial;
    private Scene escenaSeleccionMorty;
    private Scene escenaCombate;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Rick and Morty Combat");
        
        crearEscenaInicial();
        primaryStage.setScene(escenaInicial);
        primaryStage.show();
    }

    private void crearEscenaInicial() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Rick and Morty Combat");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField nombreInput = new TextField();
        nombreInput.setPromptText("Ingresa tu nombre");
        nombreInput.setMaxWidth(200);

        Button comenzarBtn = new Button("Comenzar Aventura");
        comenzarBtn.setOnAction(e -> {
            String nombre = nombreInput.getText();
            if (!nombre.isEmpty()) {
                jugador = new Rick(nombre, "C-137");
                crearEscenaSeleccionMorty();
                primaryStage.setScene(escenaSeleccionMorty);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Nombre requerido");
                alert.setContentText("Por favor, ingresa un nombre para comenzar la aventura.");
                alert.showAndWait();
            }
        });
        

        root.getChildren().addAll(titulo, nombreInput, comenzarBtn);
        escenaInicial = new Scene(root, 800, 600);
    }

    private void crearEscenaSeleccionMorty() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titulo = new Label("Selecciona tu Morty");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane gridMortys = new GridPane();
        gridMortys.setHgap(20);
        gridMortys.setVgap(20);
        gridMortys.setAlignment(Pos.CENTER);

        crearTarjetaMorty(gridMortys, new MortyAtacante("Morty Atacante", 1), 0, 0);
        crearTarjetaMorty(gridMortys, new MortyDefensivo("Morty Defensivo", 1), 0, 1);
        crearTarjetaMorty(gridMortys, new MortyCurador("Morty Curador", 1), 1, 0);
        crearTarjetaMorty(gridMortys, new MortyEquilibrado("Morty Equilibrado", 1), 1, 1);

        root.getChildren().addAll(titulo, gridMortys);
        escenaSeleccionMorty = new Scene(root, 800, 600);
    }

    private void crearTarjetaMorty(GridPane grid, Morty morty, int row, int col) {
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
        tarjeta.setAlignment(Pos.CENTER);

        Label nombreMorty = new Label(morty.getNombre());
        nombreMorty.setStyle("-fx-font-weight: bold;");

        VBox stats = new VBox(5);
        stats.getChildren().addAll(
            new Text("Salud: " + morty.getSaludActual()),
            new Text("Nivel: " + morty.getNivel())
        );

        Button seleccionarBtn = new Button("Seleccionar");
        seleccionarBtn.setOnAction(e -> {
            jugador.seleccionarMorty(morty);
            crearEscenaCombate();
            primaryStage.setScene(escenaCombate);
        });

        tarjeta.getChildren().addAll(nombreMorty, stats, seleccionarBtn);
        grid.add(tarjeta, col, row);
    }

    private void crearEscenaCombate() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
    
        // Crear sistema de combate
        Morty mortyEnemigo = new MortyEquilibrado("Morty Salvaje", 1); // Ejemplo de enemigo
        SistemaCombate sistemaCombate = new SistemaCombate(jugador.getMortySeleccionado(), mortyEnemigo);
    
        // Panel superior - Información del combate
        HBox infoCombate = new HBox(20);
        infoCombate.setAlignment(Pos.CENTER);
        
        // Barras de vida
        ProgressBar vidaJugador = new ProgressBar(1.0);
        ProgressBar vidaEnemigo = new ProgressBar(1.0);
        vidaJugador.setPrefWidth(200);
        vidaEnemigo.setPrefWidth(200);
    
        VBox infoJugador = new VBox(5);
        infoJugador.setAlignment(Pos.CENTER);
        Label nombreJugador = new Label(jugador.getMortySeleccionado().getNombre());
        infoJugador.getChildren().addAll(nombreJugador, vidaJugador);
    
        VBox infoEnemigo = new VBox(5);
        infoEnemigo.setAlignment(Pos.CENTER);
        Label nombreEnemigo = new Label("Morty Salvaje");
        infoEnemigo.getChildren().addAll(nombreEnemigo, vidaEnemigo);
    
        infoCombate.getChildren().addAll(infoJugador, new Label("VS"), infoEnemigo);
        root.setTop(infoCombate);
    
        // Panel central - Log de combate
        TextArea logCombate = new TextArea();
        logCombate.setEditable(false);
        logCombate.setPrefRowCount(3);
        logCombate.setWrapText(true);
        
        // Vincular el mensaje de combate
        sistemaCombate.mensajeCombateProperty().addListener((obs, old, nuevo) -> {
            logCombate.appendText(nuevo + "\n");
        });
        
        root.setCenter(logCombate);
    
        // Panel inferior - Controles de combate
        VBox controles = new VBox(10);
        controles.setAlignment(Pos.CENTER);
        
        HBox botonesHabilidades = new HBox(10);
        botonesHabilidades.setAlignment(Pos.CENTER);
        
        // Crear botones para cada habilidad
        for (Habilidad habilidad : jugador.getMortySeleccionado().getHabilidades()) {
            Button btnHabilidad = new Button(habilidad.getNombre());
            btnHabilidad.setOnAction(e -> {
                if (sistemaCombate.isTurnoJugador() && !sistemaCombate.isCombateTerminado()) {
                    if (sistemaCombate.ejecutarTurnoJugador(habilidad)) {
                        // Actualizar barras de vida
                        actualizarBarrasVida(vidaJugador, vidaEnemigo, sistemaCombate);
                        
                        // Si el combate no ha terminado, ejecutar turno del enemigo
                        if (!sistemaCombate.isCombateTerminado()) {
                            // Pequeña pausa antes del turno enemigo
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> {
                                sistemaCombate.ejecutarTurnoEnemigo();
                                actualizarBarrasVida(vidaJugador, vidaEnemigo, sistemaCombate);
                            });
                            pause.play();
                        } else {
                            mostrarResultadoCombate(sistemaCombate);
                        }
                    }
                }
            });
            botonesHabilidades.getChildren().add(btnHabilidad);
        }
    
        controles.getChildren().add(botonesHabilidades);
        root.setBottom(controles);
    
        escenaCombate = new Scene(root, 800, 600);
    }
    
    private void actualizarBarrasVida(ProgressBar vidaJugador, ProgressBar vidaEnemigo, SistemaCombate sistemaCombate) {
        Morty jugador = sistemaCombate.getMortyJugador();
        Morty enemigo = sistemaCombate.getMortyEnemigo();
        
        vidaJugador.setProgress(jugador.getSaludMaxima() > 0 ? (double) jugador.getSaludActual() / jugador.getSaludMaxima() : 0);
        vidaEnemigo.setProgress(enemigo.getSaludMaxima() > 0 ? (double) enemigo.getSaludActual() / enemigo.getSaludMaxima() : 0);

    }
    
    private void mostrarResultadoCombate(SistemaCombate sistemaCombate) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Combate Terminado");
        alert.setHeaderText(null);
        alert.setContentText(sistemaCombate.mensajeCombateProperty().get());
        
        alert.showAndWait().ifPresent(response -> {
            // Volver a la pantalla de selección de Morty
            crearEscenaSeleccionMorty();
            primaryStage.setScene(escenaSeleccionMorty);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}