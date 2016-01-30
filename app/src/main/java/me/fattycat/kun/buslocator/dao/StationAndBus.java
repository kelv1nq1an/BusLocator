/*
 * Copyright (C) 2016 FattycatR<kelv1nq1an>
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.fattycat.kun.buslocator.dao;

import me.fattycat.kun.buslocator.model.BusGPSEntity;
import me.fattycat.kun.buslocator.model.StationListEntity;

/**
 * Author: Kelvinkun
 * Time: 16/1/30
 * Descirption:
 */
public class StationAndBus {
    public final static int DAO_TYPE_STATION = 1;
    public final static int DAO_TYPE_BUS = 2;
    public final static int DAO_STATE_ARRIVE = 1;
    public final static int DAO_STATE_LEAVE = 2;

    public int type = 0;
    public int busState = 0;
    public BusGPSEntity.ResultEntity.ListsEntity bus;
    public StationListEntity.ResultEntity.StationEntity station;

}
