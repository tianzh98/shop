const columns = (data, hasWidth = false) => {
  let columns = [];
  data.showFields.map(item => {
    let column = {};
    column.label = data.fieldNameDictionary[item];
    column.prop = item;

    // 对应列的最小宽度，与 width 的区别是 width 是固定的，min-width 会把剩余宽度按比例分配给设置了 min-width 的列
    column.minWidth =
      data.userCustomizeWidthDictionary &&
      data.userCustomizeWidthDictionary[item]
        ? data.userCustomizeWidthDictionary[item]
        : data.width
        ? data.width[item]
        : hasWidth
          ? (column.label.length + 1) * 21
          : null;
    column.sort = data.orderByDictionary[item] ? true : false;
    columns.push(column);
  });
  return columns;
};
export default columns;
