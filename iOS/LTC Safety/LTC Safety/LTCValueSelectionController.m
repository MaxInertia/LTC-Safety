//
//  LTCValueSelectionController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCValueSelectionController.h"
#import "LTCInputAlertController.h"
#import "LTCValueSelectionViewModel.h"
#
#import "LTCRowValue.h"

@interface LTCValueSelectionController () <LTCInputAlertControllerDelegate>
@property (nonatomic, strong) LTCValueSelectionViewModel *viewModel;
@end

@implementation LTCValueSelectionController
@dynamic fileName;

- (NSString *)fileName {
    NSAssert(NO, @"Subclasses of %@ must override - fileName", self.class);
    return nil;
}

- (instancetype)init {
    
    LTCValueSelectionViewModel *viewModel = [LTCValueSelectionViewModel selectionViewModelForFileName:self.fileName];

    XLFormDescriptor *form;
    XLFormSectionDescriptor *section;
    XLFormRowDescriptor *row;
    
    NSAssert(viewModel.title.length > 0, @"Value selection controller received a view model with an empty title.");

    form = [XLFormDescriptor formDescriptorWithTitle:viewModel.title];
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    NSAssert(viewModel.rowValues.count > 0, @"Value selection controller received a view model with no rows.");
    
    for (LTCRowValue *rowValue in viewModel.rowValues) {
        row = [self _createRow:rowValue action:@selector(_selectCategory:)];
        [section addFormRow: row];
    }
    
    section = [XLFormSectionDescriptor formSection];
    [form addFormSection:section];
    
    LTCRowValue *otherRowValue = [[LTCRowValue alloc] initWithTag:@"VALUE_OTHER"];
    row = [self _createRow:otherRowValue action:@selector(_selectOther:)];
    [section addFormRow: row];
    
    if (self = [super initWithForm:form]) {
        self.viewModel = viewModel;
    }
    NSAssert1(self != nil, @"Failed to initialize %@", self.class);
    NSAssert(self.form, @"Value selection controller finished initialization with a nil form");

    return self;
}

- (XLFormRowDescriptor *)_createRow:(LTCRowValue *)rowValue action:(SEL)action {
    
    NSAssert(rowValue != nil, @"Attempted to create a row with a nil row value.");
    NSAssert(rowValue.title != nil, @"Attempted to create a row with a nil row value title.");
    NSAssert(rowValue.tag != nil, @"Attempted to create a row with a nil row value tag.");
    NSAssert2([self respondsToSelector:action], @"Attempted to create a row with an action that this %@ does not respond to: %@", self.class, NSStringFromSelector(action));
    
    XLFormRowDescriptor *row = [XLFormRowDescriptor formRowDescriptorWithTag:rowValue.tag rowType:XLFormRowDescriptorTypeButton title:rowValue.title];
    [row.cellConfig setObject:@(NSTextAlignmentLeft) forKey:@"textLabel.textAlignment"];
    [row.cellConfig setObject:[UIColor blackColor] forKey:@"textLabel.textColor"];
    row.action.formSelector = action;
    return row;
}

- (void)_selectCategory:(XLFormRowDescriptor *)categoryDescriptor {

    NSAssert(categoryDescriptor != nil, @"Selected a nil category descriptor.");
    NSAssert(categoryDescriptor.title != nil, @"Selected a category descriptor with a nil title.");

    [self _finishWithValue:categoryDescriptor.title];
}

- (void)_selectOther:(XLFormRowDescriptor *)descriptor {
    
    NSAssert(descriptor != nil, @"Selected other descriptor with nil value.");
    NSAssert(NSLocalizedString(descriptor.tag, nil) != nil, @"Missing title for other descriptor.");

    LTCInputAlertController *inputAlert = [LTCInputAlertController alertControllerWithTitle:NSLocalizedString(descriptor.tag, nil) message:self.viewModel.otherPrompt preferredStyle:UIAlertControllerStyleAlert];
    inputAlert.delegate = self;
    
    [self presentViewController:inputAlert animated:YES completion:nil];
}

- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input {
    
    NSAssert(input.length > 0, @"Value selection controller received empty custom value.");
    
    [self _finishWithValue:input];
}

- (void)_finishWithValue:(NSString *)value {
    
    NSAssert([value isEqualToString:@"testSelectCategory"] || self.navigationController != nil, @"Attempted to pop to a nil root view controller.");
    NSAssert(value.length > 0, @"Value selection controller selected an empty value.");
    
    self.rowDescriptor.value = value;
    [self.navigationController popToRootViewControllerAnimated:YES];
    
    NSAssert(self.rowDescriptor != nil, @"Value selection controller selected a value but there was no row descriptor to assign it to.");
}

@end
