//
//  LTCConcernDetailViewModel.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernDetailViewModel.h"
#import "LTCClientApi.h"


NSString *const LTCDescriptorRetractConcern             = @"RETRACT_CONCERN";               //Retract
NSString *const LTCDetailContactInfoTitle               = @"CONTACT_INFO_TITLE";            //Contact Information
NSString *const LTCDetailConcernInfoTitle               = @"DETAIL_CONCERN_INFO_TITLE";     //Concern Information
NSString *const LTCDetailActionsTakenTitle              = @"DETAIL_ACTIONS_TAKEN_TITLE";    //Actions Taken
NSString *const LTCSubmissionDate                       = @"DETAIL_DATE_SUBMITTED";         //Date submitted
NSString *const LTCSubmissionStatus                     = @"DETAIL_CONCERN_STATUS";         //Status



@interface LTCConcernDetailViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (readwrite, nonatomic, strong) LTCConcern *concern;
@end

@implementation LTCConcernDetailViewModel








/**
 Get the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 */
- (SEL)retractCallback {
    
    // Get the retract row discriptor's formSelector
    return nil;
}


/**
 Set the retract row descriptor's callback action
 
 @pre The model must have a with the LTCDescriptorRetractConcern tag
 @param retractCallback The callback that is called when the retract concern row is tapped
 */

- (void)setRetractCallback:(SEL)retractCallback {
    
    // Set the retract row discriptor's formSelector
    
}

- (instancetype)initWithConcern:(LTCConcern *)concern {
    
    NSAssert(concern != nil, @"Attempted to initialize the detail concern view model with a nil concern.");
    if (self = [super init]) {
        
        self.clientApi = [[LTCClientApi alloc] init];
        self.concern = concern;
        XLFormSectionDescriptor *section;
        XLFormRowDescriptor *row;
        
        // Sets up the form reporter information section
        
        // Sets up the concern information section
        
        section = [XLFormSectionDescriptor formSection];
        section.title = NSLocalizedString(LTCDetailContactInfoTitle, nil);
        [self addFormSection:section];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"REPORTER_NAME" rowType:XLFormRowDescriptorTypeInfo title:concern.reporter.name];
        [section addFormRow:row];
        if(concern.reporter.email != nil){
            row = [XLFormRowDescriptor formRowDescriptorWithTag:@"EMAIL_ADDRESS" rowType:XLFormRowDescriptorTypeInfo title:concern.reporter.email];
            [section addFormRow:row];
        }
        if(concern.reporter.phoneNumber != nil){
            row = [XLFormRowDescriptor formRowDescriptorWithTag:@"PHONE_NUMBER" rowType:XLFormRowDescriptorTypeInfo title:concern.reporter.phoneNumber];
            [section addFormRow:row];
        }
        
        section = [XLFormSectionDescriptor formSection];
        
        section.title = NSLocalizedString(LTCDetailConcernInfoTitle, nil);
        [self addFormSection:section];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"CONCERN_NATURE" rowType:XLFormRowDescriptorTypeInfo title:concern.concernNature];
        [section addFormRow:row];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"FACILITY_NAME" rowType:XLFormRowDescriptorTypeInfo title:concern.location.facilityName];
        [section addFormRow:row];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"ROOM_NUMBER" rowType:XLFormRowDescriptorTypeInfo title:concern.location.roomName];
        [section addFormRow:row];
        section = [XLFormSectionDescriptor formSection];
        
        section.title = NSLocalizedString(LTCDetailActionsTakenTitle, nil);
        [self addFormSection:section];
        if(concern.actionsTaken == nil){
            row = [XLFormRowDescriptor formRowDescriptorWithTag:nil rowType:XLFormRowDescriptorTypeInfo title:@""];
        }else{
            row = [XLFormRowDescriptor formRowDescriptorWithTag:@"ACTIONS_TAKEN" rowType:XLFormRowDescriptorTypeInfo title:concern.actionsTaken];
        }
        [section addFormRow:row];
        section = [XLFormSectionDescriptor formSection];
        
        section.title = NSLocalizedString(LTCSubmissionDate, nil);
        [self addFormSection:section];
        NSString *dateString = [NSDateFormatter localizedStringFromDate: concern.statuses.firstObject.creationDate
                                                              dateStyle:NSDateFormatterMediumStyle
                                                              timeStyle:NSDateFormatterShortStyle];
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"SUBMISSION_DATE" rowType:XLFormRowDescriptorTypeInfo title:dateString];
        [section addFormRow:row];
        section = [XLFormSectionDescriptor formSection];

        section.title = NSLocalizedString(LTCSubmissionStatus, nil);
        [self addFormSection:section];
        
        row = [XLFormRowDescriptor formRowDescriptorWithTag:@"CONCERN_STATUS" rowType:XLFormRowDescriptorTypeInfo title:concern.statuses.lastObject.concernType];
        [section addFormRow:row];
        section = [XLFormSectionDescriptor formSection];

        
        [self addFormSection:section];
        
        
        // Set up the rows and columns to display the concerns data
    }
    return self;
}

@end
