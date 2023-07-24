<template>
    <li class="nav-item" :class="{'has-treeview': item.items, 'menu-open': this.childs.indexOf(routeName) >= 0}">
        <a href="#" class="nav-link" v-if="!item.route && !item.items">
            <i class="nav-icon" :class="item.iconClass" v-if="item.iconClass"></i>
            <p>
                <span v-html="item.title"></span>
                <span class="right" :class="item.iconRightClass" v-if="item.iconRightClass">{{item.iconRightText}}</span>
            </p>
        </a>
        <router-link :to="item.route" class="nav-link" v-if="item.route && !item.items"
        :class="{active: routeName == item.route.name || (selected_on && selected_on.indexOf(routeName) >= 0) }">
            <i class="nav-icon" :class="item.iconClass" v-if="item.iconClass"></i>
            <p>
                <span v-html="item.title"></span>
                <span class="right" :class="item.iconRightClass" v-if="item.iconRightClass">{{item.iconRightText}}</span>
            </p>
        </router-link>
        <a href="#" class="nav-link" v-if="item.items" :class="{'active': this.childs.indexOf(routeName) >= 0}">
            <i class="nav-icon" :class="item.iconClass" v-if="item.iconClass"></i>
            <p>
                <span v-html="item.title"></span>
                <i class="fas fa-angle-left right"></i>
            </p>
        </a>
        <ul class="nav nav-treeview" v-if="item.items" v-show="this.childs.indexOf(routeName) >= 0">
            <nav-item v-for="(it, index) in item.items" :key="index" @routeAdd="routeAdd" :item="it" :routeName="routeName"></nav-item>
        </ul>
    </li>
</template>

<script>
import NavItem from './NavItem.vue'
const {console} = window
export default {
    name: 'nav-item',
    props: ['item', 'routeName'],
    data: () => ({
        childs: [],
        selected_on: []
    }),
    mounted() {
        this.selected_on = this.item.selectedOn || this.item.selected_on || []
        if (this.selected_on) {
            this.childs = this.childs.concat(this.selected_on)
        }
        if (this.item.route) {
            this.childs.push(this.item.route.name)
        }
        for (let c in this.childs) {
            this.routeAdd(this.childs[c])
        }
    },
    components: {
        NavItem
    },
    methods: {
        routeAdd(name) {
            this.$emit('routeAdd', name)
            if (typeof this.item.items != 'undefined' && this.childs.indexOf(name) < 0) {
                this.childs.push(name);
            }
        }
    }
}
</script>
