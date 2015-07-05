'use strict';

export default class HeaderService {

    constructor($state) {
        this.$state = $state;
        this.currentData = this.$state.$current.data;
    }

    parentData() {
        var breadcrumb = [];
        this.currentData.breadcrumb.forEach((name) => {
            breadcrumb.push({
                title: this.$state.get(name).data.title,
                name: name
            });
        });
        return breadcrumb;
    }

    breadcrumbData() {
        if (this.currentData) {
            return {
                title: this.currentData.title,
                parents: this.parentData()
            };
        }
        return {};
    }

}
