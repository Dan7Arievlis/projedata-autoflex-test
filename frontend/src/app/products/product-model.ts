import { Input } from "../inputs/input-model";

export interface Product {
    id: string;
    name: string;
    value: number;
    maxProduction: number;
    totalValue: number;
    inputs: Input[];
}
