import {createAction, props} from "@ngrx/store";
import {SectorDataModel} from "../models/sectorData.model";
import {Sector} from "./sector.reducer";


export const startSaving = createAction(
  '[Sector] Start saving',
  props<{
    sectorData: SectorDataModel
  }>()
)

export const saveSuccess = createAction(
  '[Sector] Save successfully',
  props<{
    id: string
  }>()
)

export const startAllSectorFetch = createAction(
  '[Sector] Start all sectors fetch'
)

export const allSectorFetchSuccess = createAction(
  '[Sector] All sectors fetched successfully',
  props<{
    sectors: Sector[]
  }>()
)

export const endFetch = createAction(
  '[Sector] Stop fetch',
  props<{
    sectorData: SectorDataModel
  }>()
)

export const error = createAction(
  '[Sector] Error',
  props<{
    error: string
  }>()
)

export const clearError = createAction(
  '[Sector] Clear error'
)
