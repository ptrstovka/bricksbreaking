
export class Arrays {

    static randomElement<T>(array: Array<T>) : T {
        return array[Math.floor(Math.random() * array.length)];
    }

    static clear<T>(array: Array<T>) : Array<T> {
        return array.splice(0, array.length);
    }

    static copy<T>(array: Array<T>) : Array<T> {
        return array.map(item => item);
    }

}
