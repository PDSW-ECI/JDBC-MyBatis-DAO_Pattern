/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Producto;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoProducto implements DaoProducto {

    Connection con;

    public JDBCDaoProducto(Connection con) {
        this.con = con;
    }
    
    @Override
    public Producto getProducto(int idProducto) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select nombre, precio from ORD_PRODUCTOS where codigo=?");
            ps.setInt(1, idProducto);
            ResultSet rs=ps.executeQuery();
            
            if (rs.next()){
                return new Producto(idProducto, rs.getString(1), rs.getInt(2));
            }
            else{
                throw new PersistenceException("No row with the given id:"+idProducto);
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
        
    }
    
}