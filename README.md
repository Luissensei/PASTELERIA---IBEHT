# PasteleriaSystem — Ecommerce Java POO

Sistema de comercio electrónico para una pastelería, desarrollado en Java con Swing,
aplicando los cuatro pilares de la Programación Orientada a Objetos.

## Integrantes

| Nombre completo | Usuario GitHub |
|-----------------|----------------|
| (completar)     | (completar)    |
| (completar)     | (completar)    |

## Tecnologías

- **Lenguaje:** Java 17+
- **Interfaz:** Swing (Java SE)
- **Persistencia:** Archivos `.txt` en carpeta `datos/`
- **IDE:** NetBeans
- **Build:** Sin build tool (compilación directa)

## Estructura del proyecto

```
PasteleriaSystem/
├── src/pasteleriasystem/
│   ├── PasteleriaSystem.java        ← Main
│   ├── Modelo/
│   │   ├── Usuario.java             ← Clase abstracta (padre)
│   │   ├── Cliente.java             ← Hereda de Usuario
│   │   ├── Administrador.java       ← Hereda de Usuario
│   │   ├── Producto.java            ← Clase abstracta (padre)
│   │   ├── Torta.java               ← Hereda de Producto
│   │   ├── ProductoIndividual.java  ← Hereda de Producto
│   │   ├── Pago.java                ← Clase abstracta
│   │   ├── PagoEfectivo.java        ← Hereda de Pago
│   │   ├── PagoTarjeta.java         ← Hereda de Pago
│   │   ├── Carrito.java
│   │   ├── ItemCarrito.java
│   │   └── Pedido.java
│   ├── Datos/
│   │   ├── UsuarioDAO.java
│   │   ├── ProductoDAO.java
│   │   └── PedidoDAO.java
│   └── Vistas/
│       ├── Inicio.java
│       ├── Registro.java
│       ├── Catalogo.java
│       ├── VistaCarrito.java
│       ├── HistorialPedidos.java
│       └── AdminPanel.java
└── datos/                           ← Se crea automáticamente al ejecutar
    ├── usuarios.txt
    ├── productos.txt
    └── pedidos.txt
```

## Cómo ejecutar

1. Clonar el repositorio: `git clone https://github.com/usuario/repo`
2. Abrir en NetBeans: `File → Open Project`
3. Ejecutar con **F6** o clic derecho sobre `PasteleriaSystem.java → Run File`
4. La carpeta `datos/` se crea automáticamente en el directorio raíz del proyecto

## Credenciales de prueba

Al primera ejecución el sistema NO tiene usuarios. Debes registrarte desde la pantalla de inicio.

Para crear un administrador, agrega manualmente esta línea al archivo `datos/usuarios.txt`:
```
Admin,admin@pasteleria.com,1234,ADMIN
```

## Conceptos POO aplicados

| Concepto        | Dónde se aplica |
|-----------------|-----------------|
| **Herencia**    | `Usuario → Cliente / Administrador`, `Producto → Torta / ProductoIndividual`, `Pago → PagoEfectivo / PagoTarjeta` |
| **Encapsulación** | Todos los atributos son `private` con getters/setters en todas las clases del modelo |
| **Polimorfismo** | `getRol()` y `getCategoria()` con `@Override`; `pago.procesar()` decide comportamiento según subclase |
| **Abstracción** | Clases abstractas `Usuario`, `Producto` y `Pago` |
| **Colecciones** | `ArrayList` en `Carrito`, `UsuarioDAO`, `ProductoDAO`, `PedidoDAO`, `Cliente` |
| **Excepciones** | `try/catch` en stock insuficiente, parsing de archivos, validación de formularios |
