package com.example.demo.service;

import com.example.demo.model.dtos.MayorVenta;
import com.example.demo.model.dtos.ProductosDeVenta;
import com.example.demo.model.dtos.TotalVentas;
import com.example.demo.model.entities.Cliente;
import com.example.demo.model.entities.Producto;
import com.example.demo.model.entities.Venta;
import com.example.demo.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private ClienteService cliService;

    @Autowired
    private ProductoService proService;

    @Override
    public List<Venta> findAll() {
        List<Venta> ventas = repository.findAll();
        if (ventas.isEmpty()) throw new NoSuchElementException("No hay ventas registradas...");
        return ventas;
    }

    @Override
    public Venta findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("No hay venta con id: " + id)
                );
    }

    @Override
    public Venta save(Venta venta) {
        try {
            this.validVenta(venta);
            venta.setTotal(this.setMontoTotalDeVenta(venta));
            return repository.save(venta);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer guardar una venta");
        }
    }

    @Override
    public Venta edit(Long id, Venta venta) {
        Venta original = this.findById(id);
        venta.setCodigoVenta(original.getCodigoVenta());
        return repository.save(venta);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al querer eliminar una venta con id: " + id);
        }
    }

    @Override
    public List<ProductosDeVenta> findProductosByVentaId(Long codigoVenta) {
        Venta venta = this.findById(codigoVenta);
        List<Producto> productos = venta.getListaProductos();
        List<ProductosDeVenta> result = new ArrayList<>();
        ProductosDeVenta aux;
        for (Producto p : productos) {
            aux = new ProductosDeVenta();
            aux.setNombre(p.getNombre());
            aux.setCantidadDisponible(p.getCantidadDisponible());
            result.add(aux);
        }
        return result;
    }

    @Override
    public TotalVentas findByFecha(String fecha) {
        LocalDate fechaVenta = LocalDate.parse(fecha);
        List<Venta> ventasByFecha = repository.findVentasByFechaVenta(fechaVenta);
        if (ventasByFecha.isEmpty()) throw new NoSuchElementException("No hay ventas con fecha: " + fecha);

        int cantVentas = 0;
        double montoTotal = 0;

        for (Venta v : ventasByFecha) {
            cantVentas++;
            montoTotal += v.getTotal();
        }

        return new TotalVentas(fechaVenta, cantVentas, montoTotal);
    }

    @Override
    public List<MayorVenta> findMayorVenta() {
        try {
            List<Venta> ventaList = repository.findAll();
            List<Venta> mayores;
            double result = 0.0;
            List<Long> ids = new ArrayList<>();
            for (Venta v : ventaList) {
                if (result <= v.getTotal()) {
                    result = v.getTotal();
                    ids.add(v.getCodigoVenta());
                }
            }
            mayores = repository.findAllById(ids);

            List<MayorVenta> mayoresVentas = new ArrayList<>();
            for (Venta v : mayores) {
                MayorVenta venta = new MayorVenta();
                venta.setCodigoVenta(v.getCodigoVenta());
                venta.setCantidadProductos(v.getListaProductos().size());
                venta.setNombreCliente(v.getUnCliente().getNombre());
                venta.setApellidoCliente(v.getUnCliente().getApellido());
                venta.setTotal(v.getTotal());
                mayoresVentas.add(venta);
            }

            return mayoresVentas;

        } catch (Exception e) {
            throw new RuntimeException("Error al quere obtener la mayor venta...");
        }
    }

    @Transactional
    public void validVenta(Venta venta) {
        Cliente cliente = venta.getUnCliente();
        if (cliService.findById(cliente.getIdCliente()) == null) {
            throw new NoSuchElementException("No se encontró Cliente con id: " + cliente.getIdCliente());
        }

        List<Producto> listaProductos = venta.getListaProductos();

        for (Producto p : listaProductos) {
            Producto aux = proService.findById(p.getCodigoProducto());
            if (aux == null) {
                throw new NoSuchElementException("No se encontró Producto con id: " + p.getCodigoProducto());
            } else {
                Double cantidadDisponible = aux.getCantidadDisponible();
                if (cantidadDisponible <= 0) throw new RuntimeException("No hay stock para el producto: " + aux.getNombre());
                aux.setCantidadDisponible(cantidadDisponible - 1);
                proService.edit(aux.getCodigoProducto(), aux);
            }
        }
    }

    private double setMontoTotalDeVenta (Venta venta) {
        List<Producto> productos = venta.getListaProductos();
        double montoTotal = 0;
        for (Producto p : productos) {
            Producto aux = proService.findById(p.getCodigoProducto());
            montoTotal += aux.getCosto();
        }
        return montoTotal;
    }
}
